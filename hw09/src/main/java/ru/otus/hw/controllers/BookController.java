package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dtos.BookCreateDto;
import ru.otus.hw.dtos.BookDto;
import ru.otus.hw.dtos.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class BookController {

    private final BookMapper bookMapper;

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String allBooksList(Model model) {
        List<BookDto> books = bookService.findAll().stream().map(bookMapper::toDto).toList();
        model.addAttribute("books", books);
        return "books/books_list";
    }

    @GetMapping("/edit_book")
    public String editBook(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id == null) {
            BookCreateDto book = new BookCreateDto(null, null, null, null);
            model.addAttribute("book", book);
            model.addAttribute("update", false);
        } else {
            Book book = bookService.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
            model.addAttribute("book", bookMapper.fromModel(book));
            model.addAttribute("update", true);
        }

        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "books/edit_book";
    }

    @PostMapping("/update_book")
    public String updateBook(@Valid @ModelAttribute("book") BookUpdateDto bookUpdateDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/edit_book?id=%d".formatted(bookUpdateDto.getId());
        }
        Set<Long> genreIds = new HashSet<>(bookUpdateDto.getGenreIds());
        bookService.update(bookUpdateDto.getId(), bookUpdateDto.getTitle(), bookUpdateDto.getAuthorId(), genreIds);
        return "redirect:/";
    }


    @PostMapping("/create_book")
    public String createBook(@Valid @ModelAttribute("book") BookCreateDto bookCreateDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/edit_book?id=%d".formatted(bookCreateDto.getId());
        }
        Set<Long> genreIds = new HashSet<>(bookCreateDto.getGenreIds());
        bookService.insert(bookCreateDto.getTitle(), bookCreateDto.getAuthorId(), genreIds);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
