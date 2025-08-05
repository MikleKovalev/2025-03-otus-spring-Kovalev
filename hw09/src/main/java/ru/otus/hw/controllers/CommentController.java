package ru.otus.hw.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dtos.CommentDto;
import ru.otus.hw.mappers.CommentMapper;
import ru.otus.hw.services.CommentService;

import java.util.List;

@Controller
@AllArgsConstructor
public class CommentController {

    private final CommentMapper commentMapper;

    private final CommentService commentService;

    @GetMapping("/book_comments")
    public String getCommentsForBook(@RequestParam("id") long id, Model model) {
        List<CommentDto> comments = commentService.findByBook(id).stream().map(commentMapper::toDto).toList();
        model.addAttribute("comments", comments);
        return "comments/book_comments";
    }
}
