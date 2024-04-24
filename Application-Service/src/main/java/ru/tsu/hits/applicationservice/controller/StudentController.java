package ru.tsu.hits.applicationservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tsu.hits.applicationservice.dto.StudentDto;
import ru.tsu.hits.applicationservice.service.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student API")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/{id}")
    public void createStudentProfile(@PathVariable String id) {
        studentService.handleStudentUserCreatedEvent(id);
    }

    @GetMapping()
    public Page<StudentDto> getAllStudents(@RequestParam Optional<Integer> page,
                                           @RequestParam Optional<Integer> size,
                                           @RequestParam Optional<String> sort) {
        Pageable pageable = PageRequest.of(
                page.orElse(0), // Default page number
                size.orElse(25), // Default page size
                Sort.by(sort.orElse("id")).descending() // Default sort property
        );
        return studentService.getAllStudents(pageable);
    }

    @GetMapping("/byCompany/{companyId}")
    public Page<StudentDto> getStudentsByCompanyId(@PathVariable String companyId,
                                                   @RequestParam Optional<Integer> page,
                                                   @RequestParam Optional<Integer> size,
                                                   @RequestParam Optional<String> sort) {
        Pageable pageable = PageRequest.of(
                page.orElse(0), // Default page number
                size.orElse(25), // Default page size
                Sort.by(sort.orElse("id")).descending() // Default sort property
        );
        return studentService.getStudentsByCompanyId(companyId, pageable);
    }

    @PostMapping("/byIds")
    public Page<StudentDto> getStudentsByIds(@RequestBody Set<String> studentIds,
                                             @RequestParam Optional<Integer> page,
                                             @RequestParam Optional<Integer> size,
                                             @RequestParam Optional<String> sort) {
        Pageable pageable = PageRequest.of(
                page.orElse(0), // Default page number
                size.orElse(25), // Default page size
                Sort.by(sort.orElse("id")).descending() // Default sort property
        );
        return studentService.getStudentsByIds(studentIds, pageable);
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable String id) {
        return studentService.getStudentDtoById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentProfile(@PathVariable String id) {
        studentService.handleUserDeletedEvent(id);
    }

    @PostMapping("/resume")
    public ResponseEntity<String> addResume(@RequestParam("file") MultipartFile file) {
        return studentService.addResume(file);
    }

    @GetMapping("/resume/{studentId}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable String studentId) {
        return studentService.downloadResume(studentId);
    }

    @PostMapping("/{id}/languages")
    public ResponseEntity<String> addLanguages(@PathVariable String id, @RequestBody List<Long> languages) {
        studentService.addLanguages(id, languages);
        return ResponseEntity.ok("Languages added successfully");
    }

    @DeleteMapping("/{id}/languages")
    public ResponseEntity<String> removeLanguages(@PathVariable String id, @RequestBody List<Long> languages) {
        studentService.removeLanguages(id, languages);
        return ResponseEntity.ok("Languages removed successfully");
    }

    @PostMapping("/{id}/stacks")
    public ResponseEntity<String> addStacks(@PathVariable String id, @RequestBody List<Long> stacks) {
        studentService.addStacks(id, stacks);
        return ResponseEntity.ok("Stacks added successfully");
    }

    @DeleteMapping("/{id}/stacks")
    public ResponseEntity<String> removeStacks(@PathVariable String id, @RequestBody List<Long> stacks) {
        studentService.removeStacks(id, stacks);
        return ResponseEntity.ok("Stacks removed successfully");
    }

    @PostMapping("/{id}/technologies")
    public ResponseEntity<String> addTechnologies(@PathVariable String id, @RequestBody List<Long> technologies) {
        studentService.addTechnologies(id, technologies);
        return ResponseEntity.ok("Technologies added successfully");
    }

    @DeleteMapping("/{id}/technologies")
    public ResponseEntity<String> removeTechnologies(@PathVariable String id, @RequestBody List<Long> technologies) {
        studentService.removeTechnologies(id, technologies);
        return ResponseEntity.ok("Technologies removed successfully");
    }
}
