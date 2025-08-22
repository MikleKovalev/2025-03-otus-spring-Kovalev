package ru.otus.hw.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorsPageController {
    @GetMapping("/authors")
    public String allAuthorsList() {
        return "authors/authors_list";
    }
}
