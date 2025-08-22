package ru.otus.hw.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dtos.GenreDto;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RestController
@AllArgsConstructor
public class GenreController {

    private final GenreMapper genreMapper;

    private final GenreService genreService;

    @GetMapping("api/genres")
    public List<GenreDto> getGenres() {
        return genreService.findAll().stream().map(genreMapper::toDto).toList();
    }
}