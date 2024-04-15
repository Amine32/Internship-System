package ru.tsu.hits.applicationservice.dto.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.applicationservice.dto.ApplicationDto;
import ru.tsu.hits.applicationservice.dto.InterviewDto;
import ru.tsu.hits.applicationservice.dto.PositionInfoDto;
import ru.tsu.hits.applicationservice.dto.StatusHistoryDto;
import ru.tsu.hits.applicationservice.model.ApplicationEntity;
import ru.tsu.hits.applicationservice.model.InterviewEntity;
import ru.tsu.hits.applicationservice.model.StatusHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final WebClient.Builder webClientBuilder;

    static {
        webClientBuilder = WebClient.builder();
    }

    public static ApplicationDto convertEntityToDto(ApplicationEntity application, HttpServletRequest request) {
        ApplicationDto dto = modelMapper.map(application, ApplicationDto.class);

        List<StatusHistory> statusHistoryList = application.getStatusHistory();
        List<StatusHistoryDto> statusHistoryDtos;

        if (statusHistoryList != null) {
            statusHistoryDtos = statusHistoryList.stream()
                    .map(StatusHistoryDtoConverter::convertEntityToDto)
                    .collect(Collectors.toList());
        } else {
            statusHistoryDtos = new ArrayList<>();
        }

        dto.setStatusHistory(statusHistoryDtos);

        List<InterviewEntity> interviews = application.getInterviews();
        List<InterviewDto> interviewDtos;

        if (interviews != null) {
            interviewDtos = interviews.stream()
                    .map(InterviewDtoConverter::convertEntityToDto)
                    .collect(Collectors.toList());
        } else {
            interviewDtos = new ArrayList<>();
        }

        dto.setInterviews(interviewDtos);

        // Create HttpHeaders instance and set the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));

        WebClient webClient = webClientBuilder.build();
        PositionInfoDto positionDto = webClient
                .get()
                .uri("http://localhost:8080/company-service/api/positions/" + application.getPositionId())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(PositionInfoDto.class)
                .block();

        if (positionDto != null) {
            dto.setPosition(positionDto.getTitle());
            dto.setCompanyName(positionDto.getCompanyName());
            dto.setCompanyId(positionDto.getCompanyId());
        }

        return dto;
    }
}
