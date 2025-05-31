package ru.tsu.hits.userservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsu.hits.internship.security.JwtUtil;

/**
 * Controller for token validation operations.
 * Uses the shared JwtUtil from Internship-Security library.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
@Tag(name = "Token Validation API")
public class TokenController {

    private final JwtUtil jwtUtil; // This comes from Internship-Security

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody String token) {
        try {
            boolean isValid = jwtUtil.validateJwtToken(token);
            if (isValid) {
                return ResponseEntity.ok(new TokenValidationResponse(
                        isValid,
                        jwtUtil.getUserNameFromJwtToken(token),
                        jwtUtil.getUserIdFromJwtToken(token),
                        jwtUtil.getAuthoritiesFromJwtToken(token).stream()
                                .map(auth -> auth.getAuthority())
                                .toList()
                ));
            }
            return ResponseEntity.ok(new TokenValidationResponse(false, null, null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid token: " + e.getMessage());
        }
    }

    // Inner class for response
    public record TokenValidationResponse(
            boolean valid,
            String username,
            String userId,
            java.util.List<String> authorities
    ) {}
}