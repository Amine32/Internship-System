package ru.tsu.hits.internshipapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.internshipapplication.dto.ApplicationDto;
import ru.tsu.hits.internshipapplication.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
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
}
