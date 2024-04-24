package ru.tsu.hits.applicationservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.applicationservice.dto.ApplicationDto;
import ru.tsu.hits.applicationservice.dto.ApplicationPriorityDto;
import ru.tsu.hits.applicationservice.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "Application API")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/{positionId}")
    public ApplicationDto createApplication(@PathVariable String positionId) {
        return applicationService.createApplication(positionId);
    }

    @GetMapping("/{applicationId}")
    public ApplicationDto getApplicationById(@PathVariable String applicationId) {
        return applicationService.getApplicationDtoById(applicationId);
    }

    @PostMapping("/{applicationId}/status/{status}")
    public ApplicationDto addStatus(@PathVariable String applicationId, @PathVariable String status) {
        return applicationService.addStatus(applicationId, status);
    }

    @GetMapping("/position/{positionId}")
    public List<ApplicationDto> getAllByPositionId(@PathVariable String positionId) {
        return applicationService.getAllByPositionId(positionId);
    }

    @DeleteMapping("/{applicationId}")
    public void deleteApplication(@PathVariable String applicationId) {
        applicationService.deleteApplicationById(applicationId);
    }

    @GetMapping("position/{positionId}/count")
    @PreAuthorize("hasRole('TRUSTED_SERVICE')")
    public Integer getCountByPositionId(@PathVariable String positionId) {
        return applicationService.getCountByPositionId(positionId);
    }

    @PutMapping("/position/{positionId}/updatePriorities")
    public void updatePriorities(@PathVariable String positionId, @RequestBody List<ApplicationPriorityDto> priorityList) {
        applicationService.updatePriorities(positionId, priorityList);
    }
}
