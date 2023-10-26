package ru.tsu.hits.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.userservice.model.UserEntity;

@Service
@RequiredArgsConstructor
public class UserNotificationService {

    private final WebClient.Builder webClientBuilder;

    public void notifyStudentService(UserEntity user) {
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/application-service/api/students/" + user.getId())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void notifyCuratorService(UserEntity user) {
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/curator-service/api/curators/" + user.getId())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void notifyStudentDeletion(String id, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + jwtToken);

        webClientBuilder.build()
                .delete()
                .uri("http://localhost:8080/application-service/api/students/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void notifyCuratorDeletion(String id, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + jwtToken);

        webClientBuilder.build()
                .delete()
                .uri("http://localhost:8080/curator-service/api/curators/" + id)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
