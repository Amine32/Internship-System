package ru.tsu.hits.internshipapplication.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.internshipapplication.dto.ApplicationDto;
import ru.tsu.hits.internshipapplication.dto.ApplicationPriorityDto;
import ru.tsu.hits.internshipapplication.service.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Api("Application API")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/{positionId}")
    public ApplicationDto createApplication(@PathVariable String positionId, HttpServletRequest request) {
        return applicationService.createApplication(positionId, request);
    }

    @GetMapping("/{applicationId}")
    public ApplicationDto getApplicationById(@PathVariable String applicationId, HttpServletRequest request) {
        return applicationService.getApplicationDtoById(applicationId, request);
    }

    @PostMapping("/{applicationId}/status/{status}")
    public ApplicationDto addStatus(@PathVariable String applicationId, @PathVariable String status, HttpServletRequest request) {
        return applicationService.addStatus(applicationId, status, request);
    }

    @GetMapping("/position/{positionId}")
    public List<ApplicationDto> getAllByPositionId(@PathVariable String positionId, HttpServletRequest request) {
        return applicationService.getAllByPositionId(positionId, request);
    }

    @DeleteMapping("/{applicationId}")
    public void deleteApplication(@PathVariable String applicationId) {
        applicationService.deleteApplicationById(applicationId);
    }

    @GetMapping("position/{positionId}/count")
    public Integer getCountByPositionId(@PathVariable String positionId) {
        return applicationService.getCountByPositionId(positionId);
    }

    @PutMapping("/position/{positionId}/updatePriorities")
    public void updatePriorities(@PathVariable String positionId, @RequestBody List<ApplicationPriorityDto> priorityList) {
        applicationService.updatePriorities(positionId, priorityList);
    }
}
