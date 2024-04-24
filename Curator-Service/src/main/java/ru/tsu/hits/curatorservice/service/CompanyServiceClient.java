package ru.tsu.hits.curatorservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.curatorservice.dto.CompanyDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceClient {

    private final WebClient.Builder webClientBuilder;

    public List<CompanyDto> getCompanies(List<String> companyIds) {

        List<CompanyDto> companies = new ArrayList<>();
        for (String companyId : companyIds) {
            CompanyDto companyDto = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8080/company-service/api/companies/" + companyId)
                    .header("Service-Name", "Company-Service")
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

