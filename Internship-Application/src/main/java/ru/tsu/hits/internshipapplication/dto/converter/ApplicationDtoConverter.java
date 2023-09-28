package ru.tsu.hits.internshipapplication.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.internshipapplication.dto.ApplicationDto;
import ru.tsu.hits.internshipapplication.dto.InterviewDto;
import ru.tsu.hits.internshipapplication.dto.PositionInfoDto;
import ru.tsu.hits.internshipapplication.dto.StatusHistoryDto;
import ru.tsu.hits.internshipapplication.model.ApplicationEntity;
import ru.tsu.hits.internshipapplication.model.InterviewEntity;
import ru.tsu.hits.internshipapplication.model.StatusHistory;

import javax.servlet.http.HttpServletRequest;
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
        }

        return dto;
    }
}
