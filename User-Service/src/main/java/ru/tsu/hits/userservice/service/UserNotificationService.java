package ru.tsu.hits.userservice.service;

import lombok.RequiredArgsConstructor;
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
                .header("Service-Name", "User-Service")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void notifyCuratorService(UserEntity user) {
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/curator-service/api/curators/" + user.getId())
                .header("Service-Name", "User-Service")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void notifyStudentDeletion(String id) {
        webClientBuilder.build()
                .delete()
                .uri("http://localhost:8080/application-service/api/students/" + id)
                .header("Service-Name", "User-Service")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void notifyCuratorDeletion(String id) {
        webClientBuilder.build()
                .delete()
                .uri("http://localhost:8080/curator-service/api/curators/" + id)
                .header("Service-Name", "User-Service")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
