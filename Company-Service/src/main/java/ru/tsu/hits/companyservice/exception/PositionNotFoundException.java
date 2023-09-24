package ru.tsu.hits.companyservice.exception;

public class PositionNotFoundException extends RuntimeException {

    public PositionNotFoundException(String message) {
        super(message);
    }
}
