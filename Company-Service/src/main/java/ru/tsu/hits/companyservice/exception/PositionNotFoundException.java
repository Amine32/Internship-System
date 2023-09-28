package ru.tsu.hits.companyservice.exception;

public class PositionNotFoundException extends EntityNotFoundException {
    public PositionNotFoundException(String id) {
        super("Position", id);
    }
}
