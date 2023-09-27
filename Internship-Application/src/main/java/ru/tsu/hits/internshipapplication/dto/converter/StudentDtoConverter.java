package ru.tsu.hits.internshipapplication.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.internshipapplication.dto.ApplicationDto;
import ru.tsu.hits.internshipapplication.dto.StackDto;
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

        List<StackDto> languageNames = fetchLanguagesByIds(student.getLanguageIds());
        List<StackDto> positionNames = fetchPositionsByIds(student.getStackIds());
        List<StackDto> technologyNames = fetchTechnologiesByIds(student.getTechnologyIds());

        // Set these to the dto
        dto.setLanguages(languageNames);
        dto.setStacks(positionNames);
        dto.setTechnologies(technologyNames);

        return dto;
    }

    private static List<StackDto> fetchLanguagesByIds(List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return webClient.post()
                .uri("http://localhost:8080/stack-service/api/languages/byIds")
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(StackDto.class)
                .collectList()
                .block();
    }

    private static List<StackDto> fetchPositionsByIds(List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return webClient.post()
                .uri("http://localhost:8080/stack-service/api/positions/byIds")
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(StackDto.class)
                .collectList()
                .block();
    }

    private static List<StackDto> fetchTechnologiesByIds(List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }

        return webClient.post()
                .uri("http://localhost:8080/stack-service/api/technologies/byIds")
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(StackDto.class)
                .collectList()
                .block();
    }
}
