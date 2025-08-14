package ru.otus.hw.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dtos.CommentDto;
import ru.otus.hw.mappers.CommentMapper;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentMapper commentMapper;

    private final CommentService commentService;

    @GetMapping("api/comments/book/{id}")
    public List<CommentDto> getCommentsForBook(@PathVariable("id") long id) {
        return commentService.findByBook(id).stream().map(commentMapper::toDto).toList();
    }
}
