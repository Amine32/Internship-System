package ru.tsu.hits.internship.common.exception;

/**
 * Exception thrown when a requested resource is not found.
 * This is a common exception that can be used across all services.
 */
public class ResourceNotFoundException extends BaseException {
    
    /**
     * Creates a new ResourceNotFoundException with the specified resource type and identifier.
     *
     * @param resourceType the type of resource that was not found (e.g., "User", "Company")
     * @param identifier the identifier used to look up the resource
     */
    public ResourceNotFoundException(String resourceType, String identifier) {
        super(String.format("%s with identifier '%s' not found", resourceType, identifier));
    }
    
    /**
     * Creates a new ResourceNotFoundException with a custom message.
     *
     * @param message the error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}