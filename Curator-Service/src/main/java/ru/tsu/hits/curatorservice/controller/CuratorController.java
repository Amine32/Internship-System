package ru.tsu.hits.curatorservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.curatorservice.dto.CuratorDto;
import ru.tsu.hits.curatorservice.service.CuratorService;

import java.util.List;


@RestController
@RequestMapping("/api/curators")
@RequiredArgsConstructor
@Tag(name = "Curator API")
public class CuratorController {

    private final CuratorService curatorService;

    @PostMapping("/{id}")
    @Operation(summary = "Create a new curator")
    public void createCurator(@PathVariable String id) {
        curatorService.handleCuratorCreatedEvent(id);
    }

    @GetMapping
    @Operation(summary = "Get all curators")
    public List<CuratorDto> getAllCurators(HttpServletRequest request) {
        return curatorService.getAllCurators(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get curator by id")
    public ResponseEntity<CuratorDto> getCuratorById(@PathVariable String id, HttpServletRequest request) {
        CuratorDto curatorDto = curatorService.getCuratorById(id, request);
        return new ResponseEntity<>(curatorDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete curator by id")
    public void deleteCurator(@PathVariable String id) {
        curatorService.handleCuratorDeletedEvent(id);
    }

    @PostMapping("/{curatorId}/companies/{companyId}")
    @Operation(summary = "Add a company to a curator")
    public void addCompany(@PathVariable String curatorId, @PathVariable String companyId) {
        curatorService.addCompanyToCurator(curatorId, companyId);
    }

    @DeleteMapping("/{curatorId}/companies/{companyId}")
    @Operation(summary = "Remove a company from a curator")
    public void removeCompany(@PathVariable String curatorId, @PathVariable String companyId) {
        curatorService.removeCompanyFromCurator(curatorId, companyId);
    }

    @GetMapping("/companies/{companyId}")
    @Operation(summary = "Get curators by company id")
    public List<CuratorDto> getCuratorsByCompanyId(@PathVariable String companyId, HttpServletRequest request) {
        return curatorService.getCuratorsByCompanyId(companyId, request);
    }
}
