package ru.tsu.hits.internship.common.exception;

import lombok.Getter;

import java.util.Map;

/**
 * Exception thrown when validation errors occur.
 * This is a common exception that can be used across all services.
 */
@Getter
public class ValidationException extends BaseException {
    
    private final Map<String, String> errors;
    
    /**
     * Creates a new ValidationException with the specified validation errors.
     *
     * @param errors a map of field names to error messages
     */
    public ValidationException(Map<String, String> errors) {
        super("Validation failed");
        this.errors = errors;
    }
    
    /**
     * Creates a new ValidationException with a single validation error.
     *
     * @param field the field that failed validation
     * @param message the error message
     */
    public ValidationException(String field, String message) {
        super("Validation failed");
        this.errors = Map.of(field, message);
    }
    
    /**
     * Creates a new ValidationException with a custom message and validation errors.
     *
     * @param message the error message
     * @param errors a map of field names to error messages
     */
    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}