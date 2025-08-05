package ru.otus.hw.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ShortBookDto {

    @NotNull(message = "Book id can't be null!")
    private Long id;

    @NotBlank(message = "Book title can't be empty!")
    private String title;

    @NotBlank(message = "Author full name can't be empty!")
    private AuthorDto author;
}
