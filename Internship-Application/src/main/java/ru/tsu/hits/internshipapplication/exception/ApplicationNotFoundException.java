package ru.tsu.hits.internshipapplication.exception;

public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
