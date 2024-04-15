package ru.tsu.hits.stackservice.dto.converter;

import ru.tsu.hits.stackservice.dto.CreateUpdateTechnologyDto;
import ru.tsu.hits.stackservice.dto.TechnologyDto;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.model.StackEntity;
import ru.tsu.hits.stackservice.model.TechnologyEntity;

import java.util.stream.Collectors;

public class TechnologyDtoConverter {

    public static TechnologyEntity dtoToEntity(CreateUpdateTechnologyDto dto) {
        TechnologyEntity entity = new TechnologyEntity();
        entity.setName(dto.getName());
        // Related stacks and languages will be set in the service layer
        return entity;
    }

    public static TechnologyDto entityToDto(TechnologyEntity entity) {
        TechnologyDto dto = new TechnologyDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRelatedStackIds(entity.getRelatedStacks().stream()
                .map(StackEntity::getId)
                .collect(Collectors.toList()));
        dto.setRelatedLanguageIds(entity.getRelatedLanguages().stream()
                .map(LanguageEntity::getId)
                .collect(Collectors.toList()));
        return dto;
    }

}
