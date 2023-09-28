package ru.geekbrains.gym.exceptions;

public class IncorrectPaidPeriodException extends RuntimeException {
    private static final String message = "Please specify correct paid period";

    public IncorrectPaidPeriodException() {
        super(message);
    }
}
