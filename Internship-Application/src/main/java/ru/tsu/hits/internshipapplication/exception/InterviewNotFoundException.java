package ru.tsu.hits.internshipapplication.exception;

public class InterviewNotFoundException extends RuntimeException{
    public InterviewNotFoundException(String message) {
        super(message);
    }
}
