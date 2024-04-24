package ru.tsu.hits.userservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.tsu.hits.userservice.exception.JwtTokenExpiredException;
import ru.tsu.hits.userservice.exception.JwtTokenMalformedException;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String encodedSecretKey;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(encodedSecretKey);
        key = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 24);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId", userDetails.getUserId())
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    public List<SimpleGrantedAuthority> getAuthoritiesFromJwtToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        List<?> roles = claims.getBody().get("authorities", List.class);
        if (roles == null) {
            throw new JwtException("Claims 'authorities' is missing");
        }

        return roles.stream()
                .filter(obj -> obj instanceof String)
                .map(obj -> new SimpleGrantedAuthority((String) obj))
                .collect(Collectors.toList());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException("JWT Token has expired");
        } catch (MalformedJwtException e) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (Exception e) {
            throw new JwtTokenMalformedException("Error processing JWT token: " + e.getMessage());
        }
    }
}

