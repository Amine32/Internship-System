
package ru.tsu.hits.internship.exception;

/**
 * Exception thrown when a JWT token is malformed.
 */
public class JwtTokenMalformedException extends RuntimeException {
    public JwtTokenMalformedException(String message) {
        super(message);
    }
}