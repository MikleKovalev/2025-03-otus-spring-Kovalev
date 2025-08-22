package ru.otus.hw.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CommentsPageController {

    @GetMapping("/book_comments/{id}")
    public String getCommentsForBook(@PathVariable("id") long id, Model model) {
        model.addAttribute("bookId", id);
        return "comments/book_comments";
    }
}
