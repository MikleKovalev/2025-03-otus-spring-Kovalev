package ru.otus.hw.domain;

import ru.otus.hw.exceptions.NoCorrectAnswerException;

import java.util.List;

public record Question(String text, List<Answer> answers) {
    public int getCorrectAnswerNumber() throws NoCorrectAnswerException {
        for (int i = 0; i < answers.size(); ++i) {
            if (answers.get(i).isCorrect()) {
                return i;
            }
        }
        throw new NoCorrectAnswerException("No correct answer found");
    }
}
