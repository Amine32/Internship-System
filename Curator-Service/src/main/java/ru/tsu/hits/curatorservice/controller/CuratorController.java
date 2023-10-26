package ru.tsu.hits.curatorservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.curatorservice.dto.CuratorDto;
import ru.tsu.hits.curatorservice.service.CuratorService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api/curators")
@RequiredArgsConstructor
@Api(tags = "Curator API")
public class CuratorController {

    private final CuratorService curatorService;

    @PostMapping("/{id}")
    @ApiOperation("Create a new curator")
    public void createCurator(@PathVariable String id) {
        curatorService.handleCuratorCreatedEvent(id);
    }

    @GetMapping
    @ApiOperation("Get all curators")
    public ResponseEntity<List<CuratorDto>> getAllCurators(HttpServletRequest request) {
        List<CuratorDto> curators = curatorService.getAllCurators(request);
        return new ResponseEntity<>(curators, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get curator by id")
    public ResponseEntity<CuratorDto> getCuratorById(@PathVariable String id, HttpServletRequest request) {
        CuratorDto curatorDto = curatorService.getCuratorById(id, request);
        return new ResponseEntity<>(curatorDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete curator by id")
    public void deleteCurator(@PathVariable String id) {
        curatorService.handleCuratorDeletedEvent(id);
    }

    @PostMapping("/{curatorId}/companies/{companyId}")
    @ApiOperation("Add a company to a curator")
    public void addCompany(@PathVariable String curatorId, @PathVariable String companyId) {
        curatorService.addCompanyToCurator(curatorId, companyId);
    }
}
