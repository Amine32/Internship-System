package ru.tsu.hits.internshipapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tsu.hits.internshipapplication.dto.StudentDto;
import ru.tsu.hits.internshipapplication.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable String id, HttpServletRequest request) {
        return studentService.getStudentDtoById(id, request);
    }

    @PostMapping("/{id}")
    public void createStudentProfile(@PathVariable String id) {
        studentService.handleStudentUserCreatedEvent(id);
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

    @PostMapping("/{id}/languages")
    public ResponseEntity<String> addLanguages(@PathVariable String id, @RequestBody List<String> languages) {
        studentService.addLanguages(id, languages);
        return ResponseEntity.ok("Languages added successfully");
    }

    @DeleteMapping("/{id}/languages")
    public ResponseEntity<String> removeLanguages(@PathVariable String id, @RequestBody List<String> languages) {
        studentService.removeLanguages(id, languages);
        return ResponseEntity.ok("Languages removed successfully");
    }

    @PostMapping("/{id}/stacks")
    public ResponseEntity<String> addStacks(@PathVariable String id, @RequestBody List<String> stacks) {
        studentService.addStacks(id, stacks);
        return ResponseEntity.ok("Stacks added successfully");
    }

    @DeleteMapping("/{id}/stacks")
    public ResponseEntity<String> removeStacks(@PathVariable String id, @RequestBody List<String> stacks) {
        studentService.removeStacks(id, stacks);
        return ResponseEntity.ok("Stacks removed successfully");
    }

    @PostMapping("/{id}/technologies")
    public ResponseEntity<String> addTechnologies(@PathVariable String id, @RequestBody List<String> technologies) {
        studentService.addTechnologies(id, technologies);
        return ResponseEntity.ok("Technologies added successfully");
    }

    @DeleteMapping("/{id}/technologies")
    public ResponseEntity<String> removeTechnologies(@PathVariable String id, @RequestBody List<String> technologies) {
        studentService.removeTechnologies(id, technologies);
        return ResponseEntity.ok("Technologies removed successfully");
    }
}
