package ru.tsu.hits.companyservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.companyservice.exception.CompanyNotFoundException;
import ru.tsu.hits.companyservice.model.CompanyEntity;
import ru.tsu.hits.companyservice.repository.CompanyRepository;

@Service
@RequiredArgsConstructor
public class SharedService {

    private final CompanyRepository companyRepository;

    public CompanyEntity fetchCompanyEntity(String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found"));
    }

}
