package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private static final int BUFFER_SIZE = 32768;

    private static final int SKIP_LINES_IN_CSV = 1;

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() throws QuestionReadException {
        try (var inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(fileNameProvider.getTestFileName());
             var reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8),
                     BUFFER_SIZE)) {
            return new CsvToBeanBuilder<QuestionDto>(reader)
                    .withSkipLines(SKIP_LINES_IN_CSV)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .build()
                    .parse()
                    .stream()
                    .map(QuestionDto::toDomainObject)
                    .toList();
        } catch (Exception e) {
            throw new QuestionReadException(e.getMessage());
        }
    }
}