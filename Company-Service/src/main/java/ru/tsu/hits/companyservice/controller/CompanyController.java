package ru.tsu.hits.companyservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.companyservice.dto.CompanyDto;
import ru.tsu.hits.companyservice.dto.CreateUpdateCompanyDto;
import ru.tsu.hits.companyservice.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Company API")
public class CompanyController {

    private final CompanyService companyService;
    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @PostMapping
    @Operation(summary = "Create a new company")
    public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CreateUpdateCompanyDto dto, HttpServletRequest request) {
        logger.info("Creating new company");
        CompanyDto newCompany = companyService.createCompany(dto, request);
        return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a company by its ID")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable String id, HttpServletRequest request) {
        logger.info("Fetching company with id {}", id);
        CompanyDto company = companyService.getCompanyById(id, request);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all companies")
    public ResponseEntity<List<CompanyDto>> getAllCompanies(HttpServletRequest request) {
        logger.info("Fetching all companies");
        List<CompanyDto> companies = companyService.getAllCompanies(request);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a company by ID")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable String id, @Valid @RequestBody CreateUpdateCompanyDto dto, HttpServletRequest request) {
        logger.info("Updating company with ID {}", id);
        CompanyDto updatedCompany = companyService.updateCompany(id, dto, request);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a company by ID")
    public ResponseEntity<Void> deleteCompany(@PathVariable String id) {
        logger.info("Deleting company with ID {}", id);
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
