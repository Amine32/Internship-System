package ru.tsu.hits.companyservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final WebClient.Builder webClientBuilder;

    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // Skip JWT extraction and validation for excluded paths
        if (!isExcluded(path, method)) {
            final String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwt = authorizationHeader.substring(7);

                // Call the validation endpoint
                Boolean isValid = webClientBuilder.build()
                        .post()
                        .uri("http://localhost:8080/user-service/api/validate")
                        .body(Mono.just(jwt), String.class)
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();

                if (Boolean.TRUE.equals(isValid)) {

                    Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(jwt);
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
                    // Token is not valid, reject the request
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isExcluded(String path, String method) {
        // Exclude other paths for all methods
        for (String excludedPath : jwtProperties.getExcludedPaths()) {
            if (path.startsWith(excludedPath)) {
                return true;
            }
        }

        return false;
    }
}
