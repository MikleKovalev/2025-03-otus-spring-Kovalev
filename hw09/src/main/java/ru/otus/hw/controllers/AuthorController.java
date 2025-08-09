package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.otus.hw.dtos.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.services.AuthorServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorMapper authorMapper;

    private final AuthorServiceImpl authorService;

    @GetMapping("/authors")
    public String allAuthorsList(Model model) {
        List<AuthorDto> authors = authorService.findAll().stream().map(authorMapper::toDto).toList();
        model.addAttribute("authors", authors);
        return "authors/authors_list";
    }
}
