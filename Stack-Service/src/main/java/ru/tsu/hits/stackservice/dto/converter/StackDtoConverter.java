package ru.tsu.hits.stackservice.dto.converter;

import org.springframework.stereotype.Component;
import ru.tsu.hits.stackservice.dto.CreateUpdateStackDto;
import ru.tsu.hits.stackservice.dto.StackDto;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.model.StackEntity;
import ru.tsu.hits.stackservice.model.TechnologyEntity;

import java.util.stream.Collectors;

@Component
public class StackDtoConverter {

    public static StackEntity dtoToEntity(CreateUpdateStackDto dto) {
        StackEntity entity = new StackEntity();
        entity.setName(dto.getName());
        // Related languages and technologies will be set in the service layer
        return entity;
    }

    public static StackDto entityToDto(StackEntity entity) {
        StackDto dto = new StackDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRelatedLanguageIds(entity.getRelatedLanguages().stream()
                .map(LanguageEntity::getId)
                .collect(Collectors.toList()));
        dto.setRelatedTechnologyIds(entity.getRelatedTechnologies().stream()
                .map(TechnologyEntity::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}
