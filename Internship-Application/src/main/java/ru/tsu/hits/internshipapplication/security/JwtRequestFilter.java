package ru.tsu.hits.internshipapplication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final WebClient.Builder webClientBuilder;

    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/swagger-ui", "/v3/api-docs", "/swagger-ui.html", "/webjars", "/v2", "/swagger-resources");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip JWT extraction and validation for excluded paths
        if (EXCLUDED_PATHS.stream().noneMatch(path::startsWith)) {

            final String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwt = authorizationHeader.substring(7);

                // Call the validation endpoint
                Boolean isValid = webClientBuilder.build()
                        .post()
                        .uri("https://hits-user-service.onrender.com/api/validate")
                        .body(Mono.just(jwt), String.class)
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();

                System.out.println("Is Boolean valid: " + isValid);

                if (Boolean.TRUE.equals(isValid)) {

                    Jws<Claims> claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt);
                    String username = claims.getBody().getSubject();
                    List<Map<String, String>> authorityMaps = (List<Map<String, String>>) claims.getBody().get("authorities");

                    List<GrantedAuthority> authorities = authorityMaps.stream()
                            .map(map -> new SimpleGrantedAuthority(map.get("authority")))
                            .collect(Collectors.toList());

                    UserDetails userDetails = new SimpleUserDetails(username, authorities);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    System.out.println("Boolean is not valid");
                    // Token is not valid, reject the request
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }
}
