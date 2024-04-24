package ru.tsu.hits.companyservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.companyservice.dto.CompanyDto;
import ru.tsu.hits.companyservice.dto.CreateUpdateCompanyDto;
import ru.tsu.hits.companyservice.service.CompanyService;

import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Company API")
public class CompanyController {

    private final CompanyService companyService;
    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @PostMapping
    @Operation(summary = "Create a new company")
    public CompanyDto createCompany(@Valid @RequestBody CreateUpdateCompanyDto dto) {
        logger.info("Creating new company");
        return companyService.createCompany(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a company by its ID")
    public CompanyDto getCompanyById(@PathVariable String id) {
        logger.info("Fetching company with id {}", id);
        return companyService.getCompanyById(id);
    }

    @GetMapping
    @Operation(summary = "Get all companies")
    public Page<CompanyDto> getAllCompanies(@RequestParam Optional<Integer> page,
                                            @RequestParam Optional<Integer> size,
                                            @RequestParam Optional<String> sort) {
        logger.info("Fetching all companies");
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10), Sort.by(sort.orElse("createdAt")).descending());
        return companyService.getAllCompanies(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a company by ID")
    public CompanyDto updateCompany(@PathVariable String id, @Valid @RequestBody CreateUpdateCompanyDto dto) {
        logger.info("Updating company with ID {}", id);
        return companyService.updateCompany(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a company by ID")
    public void deleteCompany(@PathVariable String id) {
        logger.info("Deleting company with ID {}", id);
        companyService.deleteCompany(id);
    }
}
