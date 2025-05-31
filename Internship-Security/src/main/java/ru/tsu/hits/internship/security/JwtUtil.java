package ru.tsu.hits.internship.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.tsu.hits.internship.exception.JwtTokenExpiredException;
import ru.tsu.hits.internship.exception.JwtTokenMalformedException;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for JWT token operations.
 * This class only handles token validation and parsing - not generation.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Gets the signing key for JWT tokens.
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * Validates a JWT token.
     *
     * @param authToken the token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException("JWT Token has expired");
        } catch (MalformedJwtException e) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (Exception e) {
            throw new JwtTokenMalformedException("Error processing JWT token: " + e.getMessage());
        }
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Extracts the authorities from a JWT token.
     *
     * @param token the JWT token
     * @return the list of authorities
     * @throws JwtException if the authorities claim is missing
     */
    public List<SimpleGrantedAuthority> getAuthoritiesFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        List<?> roles = claims.get("authorities", List.class);
        if (roles == null) {
            throw new JwtException("Claims 'authorities' is missing");
        }

        return roles.stream()
                .filter(obj -> obj instanceof String)
                .map(obj -> new SimpleGrantedAuthority((String) obj))
                .collect(Collectors.toList());
    }

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token the JWT token
     * @return the user ID
     */
    public String getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("userId", String.class);
    }
}