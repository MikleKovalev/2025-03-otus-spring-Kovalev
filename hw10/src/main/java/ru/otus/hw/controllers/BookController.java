package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dtos.BookCreateDto;
import ru.otus.hw.dtos.BookDto;
import ru.otus.hw.dtos.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.HashSet;
import java.util.List;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookMapper bookMapper;

    private final BookService bookService;

    private final AuthorService authorService;

    private final CommentService commentService;

    private final GenreService genreService;

    @GetMapping("api/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll().stream().map(bookMapper::toDto).toList();
    }

    @GetMapping("api/books/{id}")
    public BookUpdateDto getBookById(@PathVariable("id") long id) {
        Book book = bookService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        return bookMapper.fromModel(book);
    }

    @PostMapping("api/books")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createBook(@Valid @RequestBody BookCreateDto bookDto) {
        bookService.insert(bookDto.getTitle(), bookDto.getAuthorId(), new HashSet<>(bookDto.getGenreIds()));
    }

    @PatchMapping("api/books/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable("id") long id, @Valid @RequestBody BookUpdateDto bookDto) {
        bookService.update(id, bookDto.getTitle(), bookDto.getAuthorId(), new HashSet<>(bookDto.getGenreIds()));
    }

    @DeleteMapping("api/books/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }
}
