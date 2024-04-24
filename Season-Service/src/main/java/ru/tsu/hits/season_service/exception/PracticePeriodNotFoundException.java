package ru.tsu.hits.season_service.exception;

public class PracticePeriodNotFoundException extends RuntimeException {
    public PracticePeriodNotFoundException(String message) {
        super(message);
    }
}
