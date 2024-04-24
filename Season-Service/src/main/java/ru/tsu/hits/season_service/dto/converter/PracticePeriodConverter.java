package ru.tsu.hits.season_service.dto.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.season_service.dto.CreateUpdatePracticePeriodDto;
import ru.tsu.hits.season_service.dto.PracticePeriodDto;
import ru.tsu.hits.season_service.model.PracticePeriodEntity;

@Component
@RequiredArgsConstructor
public class PracticePeriodConverter {
    private final ModelMapper modelMapper = new ModelMapper();
    private final WebClient webClient = WebClient.builder().build();

    public PracticePeriodEntity convertToEntity(CreateUpdatePracticePeriodDto dto) {
        return modelMapper.map(dto, PracticePeriodEntity.class);
    }

    public PracticePeriodDto convertToDto(PracticePeriodEntity entity) {
        PracticePeriodDto dto = modelMapper.map(entity, PracticePeriodDto.class);
        /*JsonNode contracts = fetchContracts(entity.getId()).block();
        dto.setContracts(contracts);*/
        return dto;
    }

    /*private Mono<JsonNode> fetchContracts(String periodId) {
        // Assuming an endpoint that returns contracts based on season ID
        return webClient.get()
                .uri("http://localhost:8080/practice-service/api/contracts/byPeriod/{periodId}", periodId)
                .header("Service-Name", "Season-Service")
                .retrieve()
                .bodyToMono(JsonNode.class);
    }*/
}

