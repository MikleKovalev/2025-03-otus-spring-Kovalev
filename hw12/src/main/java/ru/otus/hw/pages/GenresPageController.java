package ru.otus.hw.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GenresPageController {

    @GetMapping("/book_genres/{id}")
    public String genresForBook(@PathVariable("id") long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "genres/genres_list";
    }
}
