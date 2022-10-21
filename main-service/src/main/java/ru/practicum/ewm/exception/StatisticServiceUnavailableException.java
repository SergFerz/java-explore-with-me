package ru.practicum.ewm.exception;

public class StatisticServiceUnavailableException extends RuntimeException {
    public StatisticServiceUnavailableException(String message) {
        super(message);
    }
}
