package ru.tsu.hits.companyservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.companyservice.dto.CompanyDto;
import ru.tsu.hits.companyservice.dto.CreateUpdateCompanyDto;
import ru.tsu.hits.companyservice.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public CompanyDto createCompany(@RequestBody CreateUpdateCompanyDto dto) {
        return companyService.createCompany(dto);
    }

    @GetMapping("/{id}")
    public CompanyDto getCompanyById(@PathVariable String id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PutMapping("/{id}")
    public CompanyDto updateCompany(@PathVariable String id, @RequestBody CreateUpdateCompanyDto dto) {
        return companyService.updateCompany(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);
    }
}
