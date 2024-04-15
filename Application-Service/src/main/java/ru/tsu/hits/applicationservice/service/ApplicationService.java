package ru.tsu.hits.applicationservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.applicationservice.dto.ApplicationDto;
import ru.tsu.hits.applicationservice.dto.ApplicationPriorityDto;
import ru.tsu.hits.applicationservice.dto.converter.ApplicationDtoConverter;
import ru.tsu.hits.applicationservice.exception.ApplicationNotFoundException;
import ru.tsu.hits.applicationservice.model.*;
import ru.tsu.hits.applicationservice.repository.ApplicationRepository;

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

        application = applicationRepository.save(application);

        if (student.getApplications() == null) {
            student.setApplications(new ArrayList<>());
        }
        student.getApplications().add(application);
        studentService.updateStudent(student);

        return ApplicationDtoConverter.convertEntityToDto(application, request);
    }

    @Transactional(readOnly = true)
    public ApplicationDto getApplicationDtoById(String applicationId, HttpServletRequest request) {
        return ApplicationDtoConverter.convertEntityToDto(getApplicationById(applicationId), request);
    }

    @Transactional(readOnly = true)
    public ApplicationEntity getApplicationById(String applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(() -> new ApplicationNotFoundException("Application with id " + applicationId + " not found"));
    }

    @Transactional
    public ApplicationDto addStatus(String applicationId, String status, HttpServletRequest request) {
        ApplicationEntity application = getApplicationById(applicationId);

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus(Status.valueOf(status));
        statusHistory.setAddedAt(LocalDate.now());
        statusHistory.setApplication(application);

        if (application.getStatusHistory() == null) {
            application.setStatusHistory(new ArrayList<>());
        }

        application.getStatusHistory().add(statusHistory);
        application = applicationRepository.save(application);

        if (Status.valueOf(status) == Status.OFFER_ACCEPTED) {
            // Create HttpHeaders instance and set the Authorization header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", request.getHeader("Authorization"));

            //Call the company microservice to update the number of places left
            WebClient webClient = WebClient.builder().build();
            webClient
                    .put()
                    .uri("http://localhost:8080/company-service/api/positions/{positionId}/decrementPlacesLeft", application.getPositionId())
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }

        return ApplicationDtoConverter.convertEntityToDto(application, request);
    }


    @Transactional(readOnly = true)
    public List<ApplicationDto> getAllByPositionId(String positionId, HttpServletRequest request) {
        List<ApplicationEntity> applications = applicationRepository.findAllByPositionId(positionId);

        return applications.stream()
                .map(applicationEntity -> ApplicationDtoConverter.convertEntityToDto(applicationEntity, request))
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
