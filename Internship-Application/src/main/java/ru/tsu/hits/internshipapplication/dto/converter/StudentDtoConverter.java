package ru.tsu.hits.internshipapplication.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.internshipapplication.dto.ApplicationDto;
import ru.tsu.hits.internshipapplication.dto.StudentDto;
import ru.tsu.hits.internshipapplication.model.ApplicationEntity;
import ru.tsu.hits.internshipapplication.model.StudentProfile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final WebClient webClient = WebClient.builder().build();

    public static StudentDto convertEntityToDto(StudentProfile student, HttpServletRequest request) {
        StudentDto dto = modelMapper.map(student, StudentDto.class);
        List<ApplicationEntity> applications = student.getApplications();
        List<ApplicationDto> applicationList = applications.stream()
                .map(applicationEntity -> ApplicationDtoConverter.convertEntityToDto(applicationEntity, request))
                .collect(Collectors.toList());

        dto.setApplications(applicationList);

        List<String> languageNames = fetchLanguagesByIds(student.getLanguageIds());
        List<String> positionNames = fetchPositionsByIds(student.getStackIds());
        List<String> technologyNames = fetchTechnologiesByIds(student.getTechnologyIds());

        // Set these to the dto
        dto.setLanguages(languageNames);
        dto.setStacks(positionNames);
        dto.setTechnologies(technologyNames);

        return dto;
    }

    private static List<String> fetchLanguagesByIds(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return webClient.post()
                .uri("http://localhost:8080/stack-service/api/languages/byIds")
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();
    }

    private static List<String> fetchPositionsByIds(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return webClient.post()
                .uri("http://localhost:8080/stack-service/api/positions/byIds")
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();
    }

    private static List<String> fetchTechnologiesByIds(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return webClient.post()
                .uri("http://localhost:8080/stack-service/api/technologies/byIds")
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();
    }
}
