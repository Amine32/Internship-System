package ru.tsu.hits.applicationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.applicationservice.dto.StudentDto;
import ru.tsu.hits.applicationservice.dto.converter.StudentDtoConverter;
import ru.tsu.hits.applicationservice.exception.StudentNotFoundException;
import ru.tsu.hits.applicationservice.model.StudentProfile;
import ru.tsu.hits.applicationservice.repository.StudentRepository;
import ru.tsu.hits.applicationservice.security.CustomPrincipal;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final WebClient.Builder webClientBuilder;

    @Transactional(readOnly = true)
    public StudentDto getStudentDtoById(String id) {
        return StudentDtoConverter.convertEntityToDto(getStudentById(id));
    }

    private StudentProfile getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + "not found"));
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> getAllStudents(Pageable pageable) {
        Page<StudentProfile> students = studentRepository.findAll(pageable);
        return students.map(StudentDtoConverter::convertEntityToDto);
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> getStudentsByCompanyId(String companyId, Pageable pageable) {

        List<String> positionIds = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/company-service/api/positions/byCompany/" + companyId)
                .header("Service-Name", "Application-Service")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                })
                .block();

        if (positionIds == null || positionIds.isEmpty()) {
            return Page.empty(pageable);
        }

        Page<StudentProfile> students = studentRepository.findByApplications_PositionIdIn(positionIds, pageable);

        return students.map(StudentDtoConverter::convertEntityToDto);
    }

    @Transactional(readOnly = true)
    public StudentProfile getCurrentStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomPrincipal principal) {
            return studentRepository.findById(principal.getUserId())
                    .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + principal.getUserId()));
        }
        throw new StudentNotFoundException("No authenticated student found");
    }

    @Transactional
    public ResponseEntity<String> addResume(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected for upload");
        }

        StudentProfile studentProfile = getCurrentStudent();

        try {
            studentProfile.setResume(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read the file");
        }

        studentRepository.save(studentProfile);

        return ResponseEntity.ok("File saved successfully");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> downloadResume(String studentId) {
        StudentProfile studentProfile = getStudentById(studentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "resume" + "\"")
                .body(studentProfile.getResume());
    }

    @Transactional
    public void updateStudent(StudentProfile student) {
        studentRepository.save(student);
    }

    @Transactional
    public void handleStudentUserCreatedEvent(String id) {
        StudentProfile profile = new StudentProfile();
        profile.setId(id);
        studentRepository.save(profile);
    }

    @Transactional
    public void handleUserDeletedEvent(String id) {
        studentRepository.deleteById(id);
    }


    @Transactional
    public void addLanguages(String id, List<Long> languages) {
        StudentProfile student = getStudentById(id);
        student.getLanguageIds().addAll(languages);
        studentRepository.save(student);
    }

    @Transactional
    public void removeLanguages(String id, List<Long> languages) {
        StudentProfile student = getStudentById(id);
        student.getLanguageIds().removeAll(languages);
        studentRepository.save(student);
    }

    @Transactional
    public void addStacks(String id, List<Long> stacks) {
        StudentProfile student = getStudentById(id);
        student.getStackIds().addAll(stacks);
        studentRepository.save(student);
    }

    @Transactional
    public void removeStacks(String id, List<Long> stacks) {
        StudentProfile student = getStudentById(id);
        student.getStackIds().removeAll(stacks);
        studentRepository.save(student);
    }

    @Transactional
    public void addTechnologies(String id, List<Long> technologies) {
        StudentProfile student = getStudentById(id);
        student.getTechnologyIds().addAll(technologies);
        studentRepository.save(student);
    }

    @Transactional
    public void removeTechnologies(String id, List<Long> technologies) {
        StudentProfile student = getStudentById(id);
        student.getTechnologyIds().removeAll(technologies);
        studentRepository.save(student);
    }

    public Page<StudentDto> getStudentsByIds(Set<String> studentIds, Pageable pageable) {
        Page<StudentProfile> studentProfiles = studentRepository.findByIdIn(studentIds, pageable);
        return studentProfiles.map(StudentDtoConverter::convertEntityToDto);
    }
}
