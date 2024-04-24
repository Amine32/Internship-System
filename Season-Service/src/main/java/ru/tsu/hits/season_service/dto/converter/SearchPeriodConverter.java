package ru.tsu.hits.season_service.dto.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tsu.hits.season_service.dto.CreateUpdateSearchPeriodDto;
import ru.tsu.hits.season_service.dto.SearchPeriodDto;
import ru.tsu.hits.season_service.model.SearchPeriodEntity;

@Component
@RequiredArgsConstructor
public class SearchPeriodConverter {
    private final ModelMapper modelMapper = new ModelMapper();
    //private final WebClient webClient = WebClient.builder().build();

    public SearchPeriodEntity convertToEntity(CreateUpdateSearchPeriodDto dto) {
        return modelMapper.map(dto, SearchPeriodEntity.class);
    }

    public SearchPeriodDto convertToDto(SearchPeriodEntity entity) {
        SearchPeriodDto dto = modelMapper.map(entity, SearchPeriodDto.class);
        //JsonNode positions = fetchPositions(entity.getId).block();
        //dto.setPositions(positions);
        return dto;
    }

   /* private Mono<JsonNode> fetchPositions(String periodId) {
        return webClient.get()
                .uri("http://localhost:8080/company-service/api/positions/byPeriod/{periodId}", periodId)
                .header("Service-Name", "Season-Service")
                .retrieve()
                .bodyToMono(JsonNode.class);
    }*/
}
