package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Question;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final IOService ioService;

    @Override
    public void askQuestion(Question question) {
        ioService.printLine(question.text());
        for (int i = 0; i < question.answers().size(); ++i) {
            ioService.printFormattedLine(
                    "%d) %s",
                    i + 1,
                    question.answers().get(i).text());
        }
        ioService.printLine("What is number of the right answer?");
    }

    @Override
    public int getAnswerNumber(Question question) {
        int lastAnswerNumber = question.answers().size();
        String invalidAnswerMessage = String.format(
                "Number of answers must be between 1 and %d",
                lastAnswerNumber);
        return ioService.readIntForRange(
                1,
                lastAnswerNumber,
                invalidAnswerMessage);
    }

    @Override
    public boolean verifyAnswer(Question question, int answerNumber) {
        return question.getCorrectAnswerNumber() == answerNumber;
    }
}
