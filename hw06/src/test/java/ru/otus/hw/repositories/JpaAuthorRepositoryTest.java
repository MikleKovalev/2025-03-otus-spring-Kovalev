package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с авторами")
@DataJpaTest
@Import(JpaAuthorRepository.class)
class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    @Autowired
    private TestEntityManager em;

    private static final long FIRST_AUTHOR_ID = 1L;

    @DisplayName("должен вернуть всех авторов")
    @Test
    void findAll() {
        var findAuthors = jpaAuthorRepository.findAll();
        var expectedAuthors = getAuthorDb();

        assertThat(findAuthors).containsExactlyElementsOf(expectedAuthors);
        findAuthors.forEach(System.out::println);
    }

    private List<Author> getAuthorDb() {
        return IntStream.range(1, 4).boxed()
                .map(id -> em.find(Author.class, id))
                .toList();
    }

    @DisplayName("должен найти корректный жанр по id")
    @Test
    void findById() {
        var optionalActualGenre = jpaAuthorRepository.findById(FIRST_AUTHOR_ID);
        var expectedGenre = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}