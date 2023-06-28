package ru.tsu.hits.internshipapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.internshipapplication.dto.ApplicationDto;
import ru.tsu.hits.internshipapplication.dto.converter.ApplicationDtoConverter;
import ru.tsu.hits.internshipapplication.exception.ApplicationNotFoundException;
import ru.tsu.hits.internshipapplication.model.ApplicationEntity;
import ru.tsu.hits.internshipapplication.model.Status;
import ru.tsu.hits.internshipapplication.model.StudentProfile;
import ru.tsu.hits.internshipapplication.repository.ApplicationRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentService studentService;

    @Transactional
    public ApplicationDto createApplication(String positionId, HttpServletRequest request) {
        ApplicationEntity application = new ApplicationEntity();

        application.setId(UUID.randomUUID().toString());
        application.setPositionId(positionId);

        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.NEW);
        application.setStatus(statuses);

        StudentProfile student = studentService.getStudentByToken(request);
        application.setStudent(student);

        if(student.getApplications() == null) {
            student.setApplications(new ArrayList<>());
        }
        student.getApplications().add(application);
        studentService.updateStudent(student);

        application = applicationRepository.save(application);

        return ApplicationDtoConverter.convertEntityToDto(application);
    }

    @Transactional(readOnly = true)
    public ApplicationDto getApplicationDtoById(String applicationId) {
        return ApplicationDtoConverter.convertEntityToDto(getApplicationById(applicationId));
    }

    @Transactional(readOnly = true)
    public ApplicationEntity getApplicationById(String applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(() -> new ApplicationNotFoundException("Application with id " + applicationId + " not found"));
    }

    @Transactional
    public ApplicationDto addStatus(String applicationId, String status) {
        ApplicationEntity application = getApplicationById(applicationId);

        List<Status> statuses = application.getStatus();
        statuses.add(Status.valueOf(status));
        application.setStatus(statuses);

        application = applicationRepository.save(application);

        return ApplicationDtoConverter.convertEntityToDto(application);
    }

    public List<ApplicationDto> getAllByPositionId(String positionId) {
        List<ApplicationEntity> applications = applicationRepository.findAllByPositionId(positionId);

        return applications.stream()
                .map(ApplicationDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
