package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
import ru.otus.hw.services.CommentServiceImpl;
import ru.otus.hw.services.GenreServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Тестирование контроллера книг")
@SpringBootTest
@AutoConfigureMockMvc
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
    private CommentServiceImpl commentService;

    @MockBean
    private BookMapper bookMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Должен вернуться ошибка при поиске книги")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldGetNotFoundEntity() throws Exception {
        given(bookService.findById(NOT_CONTAIN_BOOK_ID))
                .willThrow(new EntityNotFoundException(null));

        mvc.perform(get("/api/books/%d".formatted(FIRST_BOOK_ID))
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Должен обновить старую книгу")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldUpdateBook() throws Exception {
        Book book = getExampleOfBook();
        given(bookService.findById(book.getId())).willReturn(Optional.of(book));

        BookUpdateDto bookUpdateDto = new BookUpdateDto(book.getId(), book.getTitle(),
                book.getAuthor().getId(), book.getGenres().stream().map(Genre::getId).toList());

        mvc.perform(put("/api/books")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isNoContent());
    }

    @DisplayName("Not found exception при попытке обновить книгу")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void notFoundExceptionByUpdate() throws Exception {
        given(bookService.update(1L, "Title_1", 1L, Set.of(1L))).willThrow(EntityNotFoundException.class);
        BookUpdateDto bookUpdateDto = new BookUpdateDto(1L, "Title_1",
                1L, List.of(1L));

        mvc.perform(put("/api/books".formatted(1))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDto)))
                        .andExpect(status().isNotFound());
    }

    @DisplayName("Должен добавить новую книгу")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldAddNewBook() throws Exception {
        Book book = getExampleOfBook();
        BookCreateDto bookCreateDto = new BookCreateDto(
                null,
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenres().stream().map(Genre::getId).toList());

        mvc.perform(post("/api/books")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().is2xxSuccessful());
    }

    @DisplayName("Должен выдать ошибку авторизации")
    @Test
    void shouldReturn401ForCreateBook() throws Exception {
        mvc.perform(post("/api/books")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Должен удалить книгу")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void shouldDeleteBook() throws Exception {
        mvc.perform(delete("/api/books/%d".formatted(FIRST_BOOK_ID))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    private Book getExampleOfBook() {
        given(authorService.findAll()).willReturn(List.of(new Author(1L, "A")));
        given(genreService.findAll()).willReturn(List.of(new Genre(1L, "G")));

        return new Book(FIRST_BOOK_ID, "a",
                authorService.findAll().get(0),
                genreService.findAll());
    }
}
