package ru.tsu.hits.companyservice.dto.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.companyservice.dto.CreateUpdatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.model.CompanyEntity;
import ru.tsu.hits.companyservice.model.PositionEntity;
import ru.tsu.hits.companyservice.service.ApplicationCountService;
import ru.tsu.hits.companyservice.service.SharedService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PositionDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();
    private final WebClient.Builder webClientBuilder;
    private final SharedService sharedService;
    private final ApplicationCountService applicationCountService;

    static {
        // Create a TypeMap for custom mapping
        TypeMap<PositionEntity, PositionDto> typeMap = modelMapper.createTypeMap(PositionEntity.class, PositionDto.class);
        typeMap.addMappings(mapper -> {
            mapper.map(PositionEntity::getStatus, PositionDto::setStatus); // Enum mapping
            mapper.map(src -> src.getCompany().getName(), PositionDto::setCompanyName); // Nested property mapping
        });
    }

    public PositionDto convertToDto(PositionEntity entity, HttpServletRequest request) {
        PositionDto dto = modelMapper.map(entity, PositionDto.class);

        WebClient webClient = webClientBuilder.build();

        // Fetch name for Language by ID
        if (entity.getLanguageId() != null) {
            List<Long> singleLanguageId = Collections.singletonList(entity.getLanguageId());
            List<String> languageNames = webClient.post()
                    .uri("http://localhost:8080/stack-service/api/languages/namesByIds")
                    .body(BodyInserters.fromValue(singleLanguageId))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                    })
                    .block();
            assert languageNames != null;
            dto.setLanguage(languageNames.get(0)); // Assuming there is only one
        }

        // Similar for Stack
        if(entity.getStackId() != null) {
            List<Long> singleStackId = Collections.singletonList(entity.getStackId());
            List<String> stackNames = webClient.post()
                    .uri("http://localhost:8080/stack-service/api/stacks/namesByIds")
                    .body(BodyInserters.fromValue(singleStackId))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                    })
                    .block();
            assert stackNames != null;
            dto.setStack(stackNames.get(0));
        }

        // Fetch names for Technologies by IDs
        if (entity.getTechnologiesIds() != null && !entity.getTechnologiesIds().isEmpty()) {
            List<String> technologyNames = webClient.post()
                    .uri("http://localhost:8080/stack-service/api/technologies/namesByIds")
                    .body(BodyInserters.fromValue(entity.getTechnologiesIds()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                    })
                    .block();
            dto.setTechnologies(technologyNames);
        }

        //Fetch numberOfApplications
        int numberOfApplications = applicationCountService.fetchApplicationCountFromMicroservice(entity.getId(), request);
        dto.setNumberOfApplications(numberOfApplications);

        return dto;
    }

    public PositionEntity convertToEntity(CreateUpdatePositionDto dto) {
        PositionEntity entity = new PositionEntity();

        // Manually map each field from CreateUpdatePositionDto to PositionEntity
        entity = updateEntityFromDto(entity, dto);

        // Fetch CompanyEntity based on dto.getCompanyId() and set it
        CompanyEntity companyEntity = sharedService.fetchCompanyEntity(dto.getCompanyId());
        entity.setCompany(companyEntity);

        return entity;
    }

    public PositionEntity updateEntityFromDto(PositionEntity entity, CreateUpdatePositionDto dto) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setNumberOfPlaces(dto.getNumberOfPlaces());
        entity.setSalaryRange(dto.getSalaryRange());
        entity.setLanguageId(dto.getLanguageId());
        entity.setStackId(dto.getStackId());
        entity.setTechnologiesIds(dto.getTechnologiesIds());

        return entity;
    }
}
