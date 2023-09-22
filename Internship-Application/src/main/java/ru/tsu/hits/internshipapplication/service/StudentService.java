package ru.tsu.hits.internshipapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.internshipapplication.dto.StudentDto;
import ru.tsu.hits.internshipapplication.dto.UserIdDto;
import ru.tsu.hits.internshipapplication.dto.converter.StudentDtoConverter;
import ru.tsu.hits.internshipapplication.exception.StudentNotFoundException;
import ru.tsu.hits.internshipapplication.model.StudentProfile;
import ru.tsu.hits.internshipapplication.repository.StudentRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final WebClient.Builder webClientBuilder;

    @Transactional(readOnly = true)
    public StudentDto getStudentDtoById(String id, HttpServletRequest request) {
        return StudentDtoConverter.convertEntityToDto(getStudentById(id), request);
    }

    private StudentProfile getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + "not found"));
    }

    @Transactional(readOnly = true)
    public StudentProfile getStudentByToken(HttpServletRequest request) {

        // Create HttpHeaders instance and set the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));

        // Call the validation endpoint
        UserIdDto userIdDto = webClientBuilder.build()
                .get()
                .uri("https://localhost:8080/user-service/api/users/jwt")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(UserIdDto.class)
                .block();


        assert userIdDto != null;
        return studentRepository.getById(userIdDto.getUserId());
    }

    @Transactional
    public ResponseEntity<String> addResume(MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected for upload");
        }

        StudentProfile studentProfile = getStudentByToken(request);

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
}
