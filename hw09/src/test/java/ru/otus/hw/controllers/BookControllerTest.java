package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dtos.BookCreateDto;
import ru.otus.hw.dtos.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorServiceImpl;
import ru.otus.hw.services.BookServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Тестирование контроллера книг")
@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final long FIRST_BOOK_ID = 1L;

    private static final long NOT_CONTAIN_BOOK_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookServiceImpl bookService;

    @MockBean
    private AuthorServiceImpl authorService;

    @MockBean
    private GenreServiceImpl genreService;

    @MockBean
    private BookMapper bookMapper;

    @DisplayName("Должен добавить новую книгу")
    @Test
    void shouldAddNewBook() throws Exception {
        Book book = getExampleOfBook();
        BookCreateDto bookCreateDto = new BookCreateDto(
                null,
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenres().stream().map(Genre::getId).toList());

        mvc.perform(post("/create_book").flashAttr("book", bookCreateDto))
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("Должен вернуться ошибка при поиске книги")
    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(NOT_CONTAIN_BOOK_ID))
                .willThrow(new EntityNotFoundException(null));

        mvc.perform(get("/edit_book").param("id", String.valueOf(NOT_CONTAIN_BOOK_ID)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Должен обновить старую книгу")
    @Test
    void shouldUpdateBook() throws Exception {
        Book book = getExampleOfBook();
        given(bookService.findById(book.getId())).willReturn(Optional.of(book));

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), book.getGenres().stream().map(Genre::getId).toList());

        mvc.perform(post("/update_book").flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("Not found exception при попытке обновить книгу")
    @Test
    void notFoundExceptionByUpdate() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto(null, null,
                null, null);

        mvc.perform(post("/update_book").flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/edit_book?id=%d".formatted(null)));
    }

    @DisplayName("Ошибка валидации id автора при создании книги")
    @Test
    void exceptionByCreateWithNonValidIdAuthor() throws Exception {
        Book book = getExampleOfBook();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                null, book.getGenres().stream().map(Genre::getId).toList());

        mvc.perform(post("/update_book").flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/edit_book?id=%d".formatted(book.getId())));
    }

    @DisplayName("Ошибка валидации id жанра при создании книги")
    @Test
    void exceptionByCreateWithNonValidIGenre() throws Exception {
        Book book = getExampleOfBook();
        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), null);

        mvc.perform(post("/update_book").flashAttr("book", bookUpdateDto))
                .andExpect(redirectedUrl("/edit_book?id=%d".formatted(book.getId())));
    }

    @DisplayName("Должен удалить книгу")
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(post("/delete").param("id", String.valueOf(FIRST_BOOK_ID)))
                .andExpect(redirectedUrl("/"));
    }

    private Book getExampleOfBook() {
        given(authorService.findAll()).willReturn(List.of(new Author(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new Genre(1L, "G")));

        return new Book(FIRST_BOOK_ID, "a",
                authorService.findAll().get(0),
                genreService.findAll());
    }
}
