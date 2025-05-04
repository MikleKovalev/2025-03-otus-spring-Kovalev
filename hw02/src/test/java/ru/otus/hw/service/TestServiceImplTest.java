package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    private TestService testService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("Ivan", "Ivanov");
        var rightAnswer = new Answer("right answer", true);
        var wrongAnswer = new Answer("wrong answer", false);
        var question = new Question(
                "question text",
                List.of(rightAnswer, wrongAnswer));
        given(questionDao.findAll()).willReturn(List.of(question));
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    void shouldExecute() {
        testService.executeTestFor(student);
        verify(ioService).printLine("");
        verify(ioService).printFormattedLine("Please answer the questions below%n");
    }

    @Test
    void shouldGiveRightAnswer() {
        int firstAnswerNumber = 1;
        int lastAnswerNumber = 2;
        String errorMessage = "Number of answer must be between 1 and 2";
        int rightAnswers = 1;
        given(ioService.readIntForRange(
                firstAnswerNumber,
                lastAnswerNumber,
                errorMessage)
        ).willReturn(firstAnswerNumber);
        var testResult = testService.executeTestFor(student);
        assertEquals(rightAnswers, testResult.getRightAnswersCount());
    }

    @Test
    void shouldGiveWrongAnswer() {
        int firstAnswerNumber = 1;
        int lastAnswerNumber = 2;
        String errorMessage = "Number of answer must be between 1 and 2";
        int rightAnswers = 0;
        given(ioService.readIntForRange(
                firstAnswerNumber,
                lastAnswerNumber,
                errorMessage)
        ).willReturn(lastAnswerNumber);
        var testResult = testService.executeTestFor(student);
        assertEquals(rightAnswers, testResult.getRightAnswersCount());
    }
}
