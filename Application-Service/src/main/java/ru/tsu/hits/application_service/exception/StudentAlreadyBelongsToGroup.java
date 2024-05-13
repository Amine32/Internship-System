package ru.tsu.hits.application_service.exception;

public class StudentAlreadyBelongsToGroup extends RuntimeException {

    public StudentAlreadyBelongsToGroup(String message) {
        super(message);
    }
}
