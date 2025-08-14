package ru.otus.hw.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dtos.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RestController
@AllArgsConstructor
public class GenreController {

    private final GenreMapper genreMapper;

    private final BookService bookService;

    private final GenreService genreService;

    @GetMapping("api/genres")
    public List<GenreDto> getGenres() {
        return genreService.findAll().stream().map(genreMapper::toDto).toList();
    }

    @GetMapping("api/genres/book/{id}")
    public List<GenreDto> genresListForBook(@PathVariable("id") long bookId) {
        Book book = bookService.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        return book.getGenres().stream().map(genreMapper::toDto).toList();
    }
}
