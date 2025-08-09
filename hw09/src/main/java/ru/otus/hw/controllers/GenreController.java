package ru.otus.hw.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dtos.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;

@Controller
@AllArgsConstructor
public class GenreController {

    private final GenreMapper genreMapper;

    private final BookService bookService;

    @GetMapping("/book_genres")
    public String allGenresList(@RequestParam("id") long id,  Model model) {
        Book book = bookService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        List<GenreDto> genres = book.getGenres().stream().map(genreMapper::toDto).toList();
        model.addAttribute("genres", genres);
        return "genres/genres_list";
    }
}
