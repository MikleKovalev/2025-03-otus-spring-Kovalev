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

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
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
            ioService.printLine("You failed this attempt to answer question");
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
        ioService.printLine("What is number of the right answer?");
    }

    private int getAnswerNumber(Question question) {
        int lastAnswerNumber = question.answers().size();
        String invalidAnswerMessage = String.format(
                "Number of answer must be between %d and %d",
                MIN_ANSWER_NUMBER,
                lastAnswerNumber);
        var answerNumber = ioService.readIntForRange(
                    MIN_ANSWER_NUMBER,
                    lastAnswerNumber,
                    invalidAnswerMessage);
        answerNumber -= MIN_ANSWER_NUMBER;
        return answerNumber;
    }
}
