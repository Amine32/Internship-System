package ru.tsu.hits.companyservice.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.companyservice.dto.CompanyDto;
import ru.tsu.hits.companyservice.dto.CreateUpdateCompanyDto;
import ru.tsu.hits.companyservice.service.CompanyService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Api(tags = "Company API")
public class CompanyController {

    private final CompanyService companyService;
    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @PostMapping
    @ApiOperation("Create a new company")
    public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CreateUpdateCompanyDto dto, HttpServletRequest request) {
        logger.info("Creating new company");
        CompanyDto newCompany = companyService.createCompany(dto, request);
        return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve a company by its ID")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable String id, HttpServletRequest request) {
        logger.info("Fetching company with id {}", id);
        CompanyDto company = companyService.getCompanyById(id, request);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get all companies")
    public ResponseEntity<List<CompanyDto>> getAllCompanies(HttpServletRequest request) {
        logger.info("Fetching all companies");
        List<CompanyDto> companies = companyService.getAllCompanies(request);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a company by ID")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable String id, @Valid @RequestBody CreateUpdateCompanyDto dto, HttpServletRequest request) {
        logger.info("Updating company with ID {}", id);
        CompanyDto updatedCompany = companyService.updateCompany(id, dto, request);
        return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a company by ID")
    public ResponseEntity<Void> deleteCompany(@PathVariable String id) {
        logger.info("Deleting company with ID {}", id);
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
