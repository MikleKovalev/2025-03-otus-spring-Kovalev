package ru.otus.hw.services;

import ru.otus.hw.models.Author;

import java.util.List;

public interface AuthorService {
    Author findById(long authorId);

    List<Author> findAll();
}
