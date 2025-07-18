package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями ")
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_GENRE_ID = 1L;

    @DisplayName("должен вернуть все жанры")
    @Test
    void findAll() {
        var findGenres = genreRepository.findAll();
        var expectedGenres = getGenreDb();

        assertThat(findGenres).containsExactlyElementsOf(expectedGenres);
        findGenres.forEach(System.out::println);
    }

    private List<Genre> getGenreDb() {
        return IntStream.range(1, 7).boxed()
                .map(id -> em.find(Genre.class, id))
                .toList();
    }

    @DisplayName("должен найти корректный жанр по id")
    @Test
    void findById() {
        var optionalActualGenre = genreRepository.findAllById(Set.of(FIRST_GENRE_ID)).stream().findFirst();
        var expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);

        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}