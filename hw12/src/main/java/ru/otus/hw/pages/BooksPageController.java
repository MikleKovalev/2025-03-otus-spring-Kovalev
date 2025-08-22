package ru.otus.hw.pages;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
@AllArgsConstructor
public class BooksPageController {

    @GetMapping("/")
    public String allBooksList() {
        return "books/books_list";
    }

    @GetMapping("create_book")
    public String createBook() {
        return "books/create_book.html";
    }

    @GetMapping("edit_book/{id}")
    public String editBook(@PathVariable("id") long id, Model model) {
        model.addAttribute("id", id);
        return "books/edit_book.html";
    }
}
