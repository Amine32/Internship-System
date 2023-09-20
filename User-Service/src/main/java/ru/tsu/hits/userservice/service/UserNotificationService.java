package ru.tsu.hits.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.userservice.model.UserEntity;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserNotificationService {

    private final WebClient.Builder webClientBuilder;

    public void notifyStudentService(UserEntity user) {
        webClientBuilder.build()
                .post()
                .uri("https://localhost:8092/api/students/" + user.getId())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void notifyDeletion(String id, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + jwtToken);

            webClientBuilder.build()
                    .delete()
                    .uri("https://hits-application-service.onrender.com/api/students/" + id)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
    }
}
