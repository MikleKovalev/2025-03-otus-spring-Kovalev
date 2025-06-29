package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Optional<Book> findById(long id) {
        String sql = """
                select b.id as book_id, b.title as title, a.id as author_id, a.full_name 
                as author_full_name, g.id as genre_id, g.name as genre_name 
                from books b 
                left join authors a on b.author_id = a.id 
                left join books_genres bg on bg.book_id = b.id 
                left join genres g On bg.genre_id = g.id
                where b.id = :id
                """;
        Book maybeBook = jdbc.query(sql, Map.of("id", id), new BookResultSetExtractor());
        return Optional.ofNullable(maybeBook);
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    private List<Book> getAllBooksWithoutGenres() {
        String sql = """
                     select b.id, b.title, b.author_id, a.full_name
                     from books b 
                     left join authors a on b.author_id = a.id
                     """;
        return jdbc.query(sql, new BookRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query("select book_id, genre_id from books_genres", new BookGenreRelationRowMapper());
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres, List<BookGenreRelation> relations) {
        relations.forEach(rel -> {
            booksWithoutGenres.stream().filter(b -> b.getId() == rel.bookId).findFirst()
                    .ifPresent(book -> genres.stream().filter(g -> g.getId() == rel.genreId).findFirst()
                            .ifPresent(book.getGenres()::add));
        });
    }

    private Book insert(Book book) {
        String sql = "insert into books (title, author_id) values (:title, :authorId)";
        var params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        var maybeBook = findById(book.getId());
        if (maybeBook.isEmpty()) {
            throw new EntityNotFoundException("Book with id " + book.getId() + " not found");
        }
        String sql = "update books set title = :title, author_id = :authorId where id = :id";
        jdbc.update(sql, Map.of("id", book.getId(), "title", book.getTitle(), "authorId", book.getAuthor().getId()));
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        String sql = "insert into books_genres (book_id, genre_id) values (:bookId, :genreId)";
        jdbc.batchUpdate(sql, book.getGenres().stream()
                .map(genre -> {
                    var params = new MapSqlParameterSource();
                    params.addValue("bookId", book.getId());
                    params.addValue("genreId", genre.getId());
                    return params;
                }).toArray(MapSqlParameterSource[]::new)
        );
    }

    private void removeGenresRelationsFor(Book book) {
        String sql = "delete from books_genres where book_id = :bookId";
        jdbc.update(sql, Map.of("bookId", book.getId()));
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var title = rs.getString("title");
            var authorId = rs.getLong("author_id");
            var fullName = rs.getString("full_name");
            return new Book(id, title, new Author(authorId, fullName), new ArrayList<>());
        }
    }
    
    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            Book book = null;
            while (rs.next()) {
                if (book == null) {
                    book = setupBook(rs);
                }
                Genre genre = setupGenre(rs);
                book.getGenres().add(genre);
            }
            return book;
        }

        private Book setupBook(ResultSet rs) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            Author author = setupAuthor(rs);
            book.setAuthor(author);
            book.setGenres(new ArrayList<>());
            return book;
        }

        private Author setupAuthor(ResultSet rs) throws SQLException {
            Author author = new Author();
            author.setId(rs.getLong("author_id"));
            author.setFullName(rs.getString("author_full_name"));
            return author;
        }

        private Genre setupGenre(ResultSet rs) throws SQLException {
            Genre genre = new Genre();
            genre.setId(rs.getLong("genre_id"));
            genre.setName(rs.getString("genre_name"));
            return genre;
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }

    private static class BookGenreRelationRowMapper implements RowMapper<BookGenreRelation> {

        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            var bookId = rs.getLong("book_id");
            var genreId = rs.getLong("genre_id");
            return new BookGenreRelation(bookId, genreId);
        }
    }
}
