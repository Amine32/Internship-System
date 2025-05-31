package ru.tsu.hits.internship.common.exception;

import lombok.Getter;

/**
 * Base exception class for all application-specific exceptions.
 * Provides a standardized way to include error messages and HTTP status codes.
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    /**
     * Creates a new BaseException with the specified message.
     *
     * @param message the error message
     */
    protected BaseException(String message) {
        super(message);
    }
    
    /**
     * Creates a new BaseException with the specified message and cause.
     *
     * @param message the error message
     * @param cause the cause of the exception
     */
    protected BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}