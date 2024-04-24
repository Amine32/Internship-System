package ru.tsu.hits.season_service.exception;

public class SearchPeriodNotFoundException extends RuntimeException {
    public SearchPeriodNotFoundException(String message) {
        super(message);
    }
}
