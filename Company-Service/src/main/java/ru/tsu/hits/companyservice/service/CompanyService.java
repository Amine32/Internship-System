package ru.tsu.hits.companyservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.companyservice.dto.CompanyDto;
import ru.tsu.hits.companyservice.dto.CreateUpdateCompanyDto;
import ru.tsu.hits.companyservice.dto.converter.CompanyDtoConverter;
import ru.tsu.hits.companyservice.exception.CompanyNotFoundException;
import ru.tsu.hits.companyservice.model.CompanyEntity;
import ru.tsu.hits.companyservice.repository.CompanyRepository;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyDtoConverter dtoConverters;

    public CompanyDto createCompany(CreateUpdateCompanyDto dto) {
        CompanyEntity newCompany = dtoConverters.convertToEntity(dto);
        companyRepository.save(newCompany);
        return dtoConverters.convertToDto(newCompany);
    }

    public CompanyDto getCompanyById(String id) {
        CompanyEntity company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        return dtoConverters.convertToDto(company);
    }

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(dtoConverters::convertToDto)
                .collect(Collectors.toList());
    }

    public CompanyDto updateCompany(String id, CreateUpdateCompanyDto dto) {
        CompanyEntity existingCompany = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));

        existingCompany.setName(dto.getName());
        existingCompany.setDescription(dto.getDescription());
        existingCompany.setAddress(dto.getAddress());
        existingCompany.setContacts(dto.getContacts());
        existingCompany.setWebsiteURL(dto.getWebsiteURL());
        existingCompany.setLogoURL(dto.getLogoURL());

        companyRepository.save(existingCompany);
        return dtoConverters.convertToDto(existingCompany);
    }

    public void deleteCompany(String id) {
        CompanyEntity existingCompany = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        companyRepository.delete(existingCompany);
    }
}

