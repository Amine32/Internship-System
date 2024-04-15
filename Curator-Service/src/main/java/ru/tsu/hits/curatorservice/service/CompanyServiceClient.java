package ru.tsu.hits.curatorservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.curatorservice.dto.CompanyDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceClient {

    private final WebClient.Builder webClientBuilder;

    public List<CompanyDto> getCompanies(List<String> companyIds, HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));

        List<CompanyDto> companies = new ArrayList<>();
        for (String companyId : companyIds) {
            CompanyDto companyDto = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8080/company-service/api/companies/" + companyId)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(CompanyDto.class)
                    .block();
            if (companyDto != null) {
                companies.add(companyDto);
            }
        }
        return companies;
    }
}

