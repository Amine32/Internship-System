package ru.tsu.hits.internship.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Custom principal class for storing user information in the security context.
 */
@Data
@AllArgsConstructor
public class CustomPrincipal {
    /**
     * The username of the authenticated user.
     */
    private String username;
    
    /**
     * The unique identifier of the authenticated user.
     */
    private String userId;
}