package ru.tsu.hits.applicationservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.applicationservice.dto.CreateUpdateInterviewDto;
import ru.tsu.hits.applicationservice.dto.InterviewDto;
import ru.tsu.hits.applicationservice.service.InterviewService;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
@Tag(name = "Interview API")
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/{applicationId}")
    public InterviewDto createInterview(@RequestBody CreateUpdateInterviewDto dto, @PathVariable String applicationId, HttpServletRequest request) {
        return interviewService.createInterview(dto, applicationId, request);
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
