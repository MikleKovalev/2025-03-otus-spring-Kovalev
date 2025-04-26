package ru.otus.hw.service;

import ru.otus.hw.domain.Question;

public interface QuestionService {
    void askQuestion(Question question);

    int getAnswerNumber(Question question);

    boolean verifyAnswer(Question question, int answerNumber);
}
