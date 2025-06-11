package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CsvQuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    @DisplayName(" должен прочитать один вопрос из тестового .csv файла")
    void shouldReadOnlyOneQuestionFromTestCsvFile() {
        int questionsCountExpected = 1;
        var questions = questionDao.findAll();
        assertEquals(questionsCountExpected, questions.size());
    }
}
