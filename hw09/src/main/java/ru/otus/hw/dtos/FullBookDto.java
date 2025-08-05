package ru.otus.hw.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class FullBookDto {

    @NotNull(message = "Book id can't be null!")
    private Long id;

    @NotBlank(message = "Author full name can't be empty!")
    private String authorFullName;

    @NotBlank(message = "Book title can't be empty!")
    private String title;

    private List<GenreDto> genres;

    private List<CommentDto> comments;
}
