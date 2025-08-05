package ru.otus.hw.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dtos.FullBookDto;
import ru.otus.hw.dtos.ShortBookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

@Component
@AllArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    public Book toModel(ShortBookDto dto, Author author, List<Genre> genres) {
        return new Book(0, dto.getTitle(), author, genres);
    }

    public Book toModel(FullBookDto dto, Author author, List<Genre> genres) {
        return new Book(0, dto.getTitle(), author, genres);
    }

    public ShortBookDto toShortDto(Book book) {
        return new ShortBookDto(book.getId(), book.getTitle(), authorMapper.toDto(book.getAuthor()));
    }

    public FullBookDto toFullDto(Book book) {

        return null;
    }
}
