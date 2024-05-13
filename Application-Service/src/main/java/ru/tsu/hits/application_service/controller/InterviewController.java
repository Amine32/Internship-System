package ru.tsu.hits.application_service.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.application_service.dto.CreateUpdateInterviewDto;
import ru.tsu.hits.application_service.dto.InterviewDto;
import ru.tsu.hits.application_service.service.InterviewService;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
@Tag(name = "Interview API")
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/{applicationId}")
    public InterviewDto createInterview(@RequestBody CreateUpdateInterviewDto dto, @PathVariable String applicationId) {
        return interviewService.createInterview(dto, applicationId);
    }

    @GetMapping("{id}")
    public InterviewDto getInterviewById(@PathVariable String id) {
        return interviewService.getInterviewById(id);
    }

    @DeleteMapping("{id}")
    public void deleteInterviewById(@PathVariable String id) {
        interviewService.deleteInterviewById(id);
    }
}
