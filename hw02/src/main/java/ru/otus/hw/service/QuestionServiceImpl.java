package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Question;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private static final int INVALID_ANSWER_NUMBER = -1;

    private static final int MIN_ANSWER_NUMBER = 1;

    private final IOService ioService;

    @Override
    public void askQuestion(Question question) {
        ioService.printLine(question.text());
        for (int i = 0; i < question.answers().size(); ++i) {
            ioService.printFormattedLine(
                    "%d) %s",
                    i + MIN_ANSWER_NUMBER,
                    question.answers().get(i).text());
        }
        ioService.printLine("What is number of the right answer?");
    }

    @Override
    public int getAnswerNumber(Question question) {
        int lastAnswerNumber = question.answers().size();
        String invalidAnswerMessage = String.format(
                "Number of answer must be between %d and %d",
                MIN_ANSWER_NUMBER,
                lastAnswerNumber);
        int answerNumber = INVALID_ANSWER_NUMBER;
        try {
            answerNumber = ioService.readIntForRange(
                    MIN_ANSWER_NUMBER,
                    lastAnswerNumber,
                    invalidAnswerMessage);
            answerNumber -= MIN_ANSWER_NUMBER;
        } catch (Exception e) {
            ioService.printLine("You failed this attempt to answer question");
        }
        return answerNumber;
    }

    @Override
    public boolean verifyAnswer(Question question, int answerNumber) {
        try {
            return question.getCorrectAnswerNumber() == answerNumber;
        } catch (Exception e) {
            return false;
        }
    }
}
