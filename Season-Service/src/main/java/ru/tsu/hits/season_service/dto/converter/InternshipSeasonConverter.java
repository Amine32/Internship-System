package ru.tsu.hits.season_service.dto.converter;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tsu.hits.season_service.dto.CreateUpdateInternshipSeasonDto;
import ru.tsu.hits.season_service.dto.InternshipSeasonDto;
import ru.tsu.hits.season_service.model.InternshipSeasonEntity;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class InternshipSeasonConverter {
    private final ModelMapper modelMapper = new ModelMapper();
    private final WebClient webClient = WebClient.builder().build();

    public InternshipSeasonEntity convertToEntity(CreateUpdateInternshipSeasonDto dto) {
        return modelMapper.map(dto, InternshipSeasonEntity.class);
    }

    public void updateEntityFromDto(CreateUpdateInternshipSeasonDto dto, InternshipSeasonEntity entity) {
        modelMapper.map(dto, entity);
    }

    public InternshipSeasonDto convertToDto(InternshipSeasonEntity entity) {
        InternshipSeasonDto dto = modelMapper.map(entity, InternshipSeasonDto.class);
        JsonNode students = fetchStudents(entity.getStudentIds()).block();
        dto.setStudents(students);
        return dto;
    }

    private Mono<JsonNode> fetchStudents(Set<String> studentIds) {
        return webClient.post()
                .uri("http://localhost:8080/application-service/api/students/byIds")
                .header("Service-Name", "Season-Service")
                .bodyValue(studentIds)
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}

