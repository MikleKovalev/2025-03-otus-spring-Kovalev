package ru.otus.hw.exceptions;

public class NoCorrectAnswerException extends RuntimeException {
    public NoCorrectAnswerException(String message, Throwable ex) {
        super(message, ex);
    }

    public NoCorrectAnswerException(String message) {
        super(message);
    }
}
