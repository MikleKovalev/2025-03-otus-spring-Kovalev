package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dtos.BookDto;
import ru.otus.hw.dtos.BookUpdateDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

@Component
@AllArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    public Book toModel(BookDto dto, Author author, List<Genre> genres) {
        return new Book(0, dto.getTitle(), author, genres);
    }

    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), authorMapper.toDto(book.getAuthor()));
    }

    public BookUpdateDto fromModel(Book book) {
        return new BookUpdateDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenres().stream().map(Genre::getId).toList());
    }
}
