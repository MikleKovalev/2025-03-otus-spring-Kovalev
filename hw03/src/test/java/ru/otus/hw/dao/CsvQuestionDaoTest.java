package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoTest {
    @Test
    void readQuestionsFromTestCsv() {
        TestFileNameProvider mockFileNameProvider = Mockito.mock(TestFileNameProvider.class);
        when(mockFileNameProvider.getTestFileName()).thenReturn("test-questions.csv");
        QuestionDao questionDao = new CsvQuestionDao(mockFileNameProvider);
        int questionsCountExpected = 1;
        var questions = questionDao.findAll();
        assertEquals(questionsCountExpected, questions.size());
    }
}
