package ru.tsu.hits.companyservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.tsu.hits.companyservice.dto.CompanyDto;
import ru.tsu.hits.companyservice.dto.CreateUpdateCompanyDto;
import ru.tsu.hits.companyservice.dto.converter.CompanyDtoConverter;
import ru.tsu.hits.companyservice.model.CompanyEntity;
import ru.tsu.hits.companyservice.repository.CompanyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyDtoConverter dtoConverter;
    private final SharedService sharedService;

    public CompanyDto createCompany(CreateUpdateCompanyDto dto) {
        log.info("Creating new company");
        return saveAndConvertToDto(dtoConverter.convertToEntity(dto));
    }

    public CompanyDto getCompanyById(String id) {
        log.info("Fetching company with id: {}", id);
        return dtoConverter.convertToDto(sharedService.fetchCompanyEntity(id));
    }

    public Page<CompanyDto> getAllCompanies(Pageable pageable) {
        log.info("Fetching all companies");
        return companyRepository.findAll(pageable)
                .map(dtoConverter::convertToDto);
    }

    public CompanyDto updateCompany(String id, CreateUpdateCompanyDto dto) {
        log.info("Updating company with id: {}", id);
        CompanyEntity existingCompany = sharedService.fetchCompanyEntity(id);
        updateCompanyEntity(existingCompany, dto);
        return saveAndConvertToDto(existingCompany);
    }

    public void deleteCompany(String id) {
        log.info("Deleting company with id: {}", id);
        companyRepository.delete(sharedService.fetchCompanyEntity(id));
    }

    private CompanyDto saveAndConvertToDto(CompanyEntity entity) {
        return dtoConverter.convertToDto(companyRepository.save(entity));
    }

    private void updateCompanyEntity(CompanyEntity existingCompany, CreateUpdateCompanyDto dto) {
        existingCompany.setName(dto.getName());
        existingCompany.setDescription(dto.getDescription());
        existingCompany.setAddress(dto.getAddress());
        existingCompany.setContacts(dto.getContacts());
        existingCompany.setWebsiteURL(dto.getWebsiteURL());
        existingCompany.setLogoURL(dto.getLogoURL());
    }
}

