package ru.tsu.hits.internship.exception;

/**
 * Exception thrown when a JWT token has expired.
 */
public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException(String message) {
        super(message);
    }
}