package ru.tsu.hits.internshipapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.internshipapplication.dto.CreateUpdateInterviewDto;
import ru.tsu.hits.internshipapplication.dto.InterviewDto;
import ru.tsu.hits.internshipapplication.service.InterviewService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/{applicationId}")
    public InterviewDto createInterview(@RequestBody CreateUpdateInterviewDto dto, @PathVariable String applicationId, HttpServletRequest request){
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
