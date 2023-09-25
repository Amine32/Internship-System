package ru.tsu.hits.internshipapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.internshipapplication.dto.ApplicationDto;
import ru.tsu.hits.internshipapplication.dto.ApplicationPriorityDto;
import ru.tsu.hits.internshipapplication.dto.converter.ApplicationDtoConverter;
import ru.tsu.hits.internshipapplication.exception.ApplicationNotFoundException;
import ru.tsu.hits.internshipapplication.model.*;
import ru.tsu.hits.internshipapplication.repository.ApplicationRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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

        //Initialize StatusHistory list and add initial status
        StatusHistory initialStatus = new StatusHistory();
        initialStatus.setStatus(Status.NEW);
        initialStatus.setAddedAt(LocalDate.now());
        initialStatus.setApplication(application);

        List<StatusHistory> statusHistoryList = new ArrayList<>();
        statusHistoryList.add(initialStatus);

        application.setStatusHistory(statusHistoryList);

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

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus(Status.valueOf(status));
        statusHistory.setAddedAt(LocalDate.now());
        statusHistory.setApplication(application);

        if(application.getStatusHistory() == null) {
            application.setStatusHistory(new ArrayList<>());
        }

        application.getStatusHistory().add(statusHistory);
        application = applicationRepository.save(application);

        if(Status.valueOf(status) == Status.OFFER_ACCEPTED) {
            //Call the company microservice to update the number of places left
            WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/company-service/").build();
            webClient
                    .put()
                    .uri("/api/positions/{positionId}/decrementPlacesLeft", application.getPositionId())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }

        return ApplicationDtoConverter.convertEntityToDto(application);
    }


    @Transactional(readOnly = true)
    public List<ApplicationDto> getAllByPositionId(String positionId) {
        List<ApplicationEntity> applications = applicationRepository.findAllByPositionId(positionId);

        return applications.stream()
                .map(ApplicationDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addInterview(String applicationId, InterviewEntity interview) {
        ApplicationEntity application = getApplicationById(applicationId);

        List<InterviewEntity> interviews = application.getInterviews();

        if (interviews == null) {
            interviews = new ArrayList<>();
        }

        interviews.add(interview);

        application.setInterviews(interviews);

        applicationRepository.save(application);
    }

    public void deleteApplicationById(String applicationId) {
        applicationRepository.deleteById(applicationId);
    }

    public Integer getCountByPositionId(String positionId) {
        return applicationRepository.countByPositionId(positionId);
    }

    public void updatePriorities(String positionId, List<ApplicationPriorityDto> priorityList) {
        for (ApplicationPriorityDto dto : priorityList) {
            ApplicationEntity application = applicationRepository.findById(dto.getApplicationId())
                    .orElseThrow(() -> new ApplicationNotFoundException(dto.getApplicationId()));

            // Validate that the application belongs to the specified position
            if (!application.getPositionId().equals(positionId)) {
                throw new IllegalArgumentException("Application " + dto.getApplicationId() + " does not belong to position " + positionId);
            }

            application.setPriority(dto.getPriority());
            applicationRepository.save(application);
        }
    }
}
