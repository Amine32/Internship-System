package ru.tsu.hits.stackservice.dto.converter;

import org.springframework.stereotype.Component;
import ru.tsu.hits.stackservice.dto.CreateUpdateTechnologyDto;
import ru.tsu.hits.stackservice.dto.TechnologyDto;
import ru.tsu.hits.stackservice.model.TechnologyEntity;

public class TechnologyDtoConverter {

    public static TechnologyEntity dtoToEntity(CreateUpdateTechnologyDto dto) {
        TechnologyEntity entity = new TechnologyEntity();
        entity.setName(dto.getName());
        return entity;
    }

    public static TechnologyDto entityToDto(TechnologyEntity entity) {
        TechnologyDto dto = new TechnologyDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRelatedLanguageId(entity.getRelatedLanguage().getId());
        dto.setRelatedStackId(entity.getRelatedStack().getId());
        return dto;
    }

}
