package ru.tsu.hits.internshipapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tsu.hits.internshipapplication.dto.StudentDto;
import ru.tsu.hits.internshipapplication.service.StudentService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable String id) {
        return studentService.getStudentDtoById(id);
    }

    @PostMapping("/{id}")
    public StudentDto createStudentProfile(@PathVariable String id) {
        return studentService.handleStudentUserCreatedEvent(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentProfile(@PathVariable String id) {
        studentService.handleUserDeletedEvent(id);
    }

    @PostMapping("/resume")
    public ResponseEntity<String> addResume(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        return studentService.addResume(file, request);
    }

    @GetMapping("/resume/{studentId}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable String studentId) {
        return studentService.downloadResume(studentId);
    }
}
