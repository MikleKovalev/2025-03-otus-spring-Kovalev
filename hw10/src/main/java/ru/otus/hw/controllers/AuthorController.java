package ru.otus.hw.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dtos.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.services.AuthorServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuthorController {

    private final AuthorMapper authorMapper;

    private final AuthorServiceImpl authorService;

    @GetMapping("api/authors")
    public List<AuthorDto> allAuthorsList() {
        return authorService.findAll().stream().map(authorMapper::toDto).toList();
    }
}
