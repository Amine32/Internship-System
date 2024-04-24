package ru.tsu.hits.userservice.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.tsu.hits.userservice.exception.JwtTokenExpiredException;
import ru.tsu.hits.userservice.exception.JwtTokenMalformedException;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) {

        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtUtil.validateJwtToken(jwt)) {
                String username = jwtUtil.getUserNameFromJwtToken(jwt);
                List<SimpleGrantedAuthority> authorities = jwtUtil.getAuthoritiesFromJwtToken(jwt);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response);
        } catch (JwtException e) {
            if (e instanceof ExpiredJwtException) {
                throw new JwtTokenExpiredException("JWT Token has expired");
            } else if (e instanceof MalformedJwtException) {
                throw new JwtTokenMalformedException("Invalid JWT token");
            } else if (e instanceof SignatureException) {
                throw new JwtTokenMalformedException("JWT signature does not match locally computed signature");
            } else {
                throw new JwtTokenMalformedException("Invalid JWT token");
            }
        } catch (Exception e) {
            // This block catches any other exception that could occur.
            throw new JwtTokenMalformedException("Error processing JWT token: " + e.getMessage());
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
