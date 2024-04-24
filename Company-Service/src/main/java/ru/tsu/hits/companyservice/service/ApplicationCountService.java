package ru.tsu.hits.companyservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

@Service
public class ApplicationCountService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationCountService.class);

    public int fetchApplicationCountFromMicroservice(String positionId) {
        int numberOfApplications = 0; // Default value

        WebClient webClient = WebClient.builder().build();

        try {
            Integer currentNumberOfApplications = webClient
                    .get()
                    .uri("http://localhost:8080/application-service/api/applications/position/{positionId}/count", positionId)
                    .header("Service-Name", "Loan-Service")
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();

            numberOfApplications = Optional.ofNullable(currentNumberOfApplications).orElse(0);

        } catch (WebClientResponseException e) {
            logger.error("WebClientResponseException while fetching application count. Status code: {}, Reason: {}", e.getStatusCode(), e.getResponseBodyAsString());

        } catch (WebClientException e) {
            logger.error("WebClientException while fetching application count: {}", e.getMessage());

        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching application count: {}", e.getMessage());
        }

        return numberOfApplications;
    }
}

