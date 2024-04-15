package ru.tsu.hits.applicationservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.applicationservice.dto.CreateUpdateInterviewDto;
import ru.tsu.hits.applicationservice.dto.InterviewDto;
import ru.tsu.hits.applicationservice.dto.converter.InterviewDtoConverter;
import ru.tsu.hits.applicationservice.exception.InterviewNotFoundException;
import ru.tsu.hits.applicationservice.model.InterviewEntity;
import ru.tsu.hits.applicationservice.model.Status;
import ru.tsu.hits.applicationservice.repository.InterviewRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationService applicationService;

    @Transactional
    public InterviewDto createInterview(CreateUpdateInterviewDto dto, String applicationId, HttpServletRequest request) {
        InterviewEntity interviewEntity = InterviewDtoConverter.convertDtoToEntity(dto);

        interviewEntity.setId(UUID.randomUUID().toString());
        interviewEntity.setApplication(applicationService.getApplicationById(applicationId));

        interviewEntity = interviewRepository.save(interviewEntity);

        applicationService.addInterview(applicationId, interviewEntity);
        applicationService.addStatus(applicationId, Status.INTERVIEW_IS_APPOINTED.toString(), request);

        return InterviewDtoConverter.convertEntityToDto(interviewEntity);
    }

    @Transactional(readOnly = true)
    public InterviewDto getInterviewById(String id) {
        return InterviewDtoConverter.convertEntityToDto(interviewRepository.findById(id).orElseThrow(() -> new InterviewNotFoundException("Interview with id " + id + "not found")));
    }

    @Transactional
    public void deleteInterviewById(String id) {
        interviewRepository.deleteById(id);
    }
}
