package ru.tsu.hits.internshipapplication.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.internshipapplication.dto.ApplicationDto;
import ru.tsu.hits.internshipapplication.dto.StudentDto;
import ru.tsu.hits.internshipapplication.dto.WorkPlaceDto;
import ru.tsu.hits.internshipapplication.model.ApplicationEntity;
import ru.tsu.hits.internshipapplication.model.StudentProfile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    private static final WebClient.Builder webClientBuilder;

    static {
        webClientBuilder = WebClient.builder();
    }

    public static StudentDto convertEntityToDto(StudentProfile student, HttpServletRequest request) {
        StudentDto dto = modelMapper.map(student, StudentDto.class);
        List<ApplicationEntity> applications = student.getApplications();
        List<ApplicationDto> applicationList = applications.stream()
                .map(ApplicationDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());

        dto.setApplications(applicationList);

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + jwtToken);

            WebClient webClient = webClientBuilder.build();
            WorkPlaceDto workPlace = webClient
                    .get()
                    .uri("https://practice-service.onrender.com/api/workPlaceInfo/info/" + student.getId())
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(WorkPlaceDto.class)
                    .onErrorReturn(new WorkPlaceDto())
                    .block();

            if(workPlace != null){
                dto.setCompanyName(workPlace.getCompanyName());
                dto.setPosition(workPlace.getPosition());
            }
        }

        return dto;
    }

}
