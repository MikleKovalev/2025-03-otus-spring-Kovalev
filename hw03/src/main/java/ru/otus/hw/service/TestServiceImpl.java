package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final int MIN_ANSWER_NUMBER = 1;

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            runQuestionAttempt(testResult, question);
        }
        return testResult;
    }

    private void runQuestionAttempt(TestResult testResult, Question question) {
        var isAnswerValid = false;
        try {
            askQuestion(question);
            int answerNumber = getAnswerNumber(question);
            isAnswerValid = question.answers().get(answerNumber).isCorrect();
        } catch (Exception e) {
            ioService.printLineLocalized("TestService.failed.attempt");
        }
        testResult.applyAnswer(question, isAnswerValid);
    }

    private void askQuestion(Question question) {
        ioService.printLine(question.text());
        for (int i = 0; i < question.answers().size(); ++i) {
            ioService.printFormattedLine(
                    "%d) %s",
                    i + MIN_ANSWER_NUMBER,
                    question.answers().get(i).text());
        }
        ioService.printLineLocalized("TestService.what.is.number");
    }

    private int getAnswerNumber(Question question) {
        int lastAnswerNumber = question.answers().size();
        var answerNumber = ioService.readIntForRangeLocalized(
                MIN_ANSWER_NUMBER,
                lastAnswerNumber,
                "TestService.wrong.answer.number");
        answerNumber -= MIN_ANSWER_NUMBER;
        return answerNumber;
    }
}
