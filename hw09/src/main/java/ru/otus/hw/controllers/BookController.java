package ru.otus.hw.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.dtos.FullBookDto;
import ru.otus.hw.dtos.ShortBookDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;

import java.util.List;

@Controller
@AllArgsConstructor
public class BookController {

    private final BookMapper bookMapper;

    private final BookService bookService;

    @GetMapping("/books")
    public String allBooksList(Model model) {
        List<ShortBookDto> books = bookService.findAll().stream().map(bookMapper::toShortDto).toList();
        model.addAttribute("books", books);
        return "books/books_list";
    }

    @GetMapping("/books/{id}")
    public String getBookById(@PathVariable("id") long id,  Model model) {
        FullBookDto book = bookMapper.toFullDto(bookService.findById(id).orElseThrow());
        return "books/books_list";
    }
}
