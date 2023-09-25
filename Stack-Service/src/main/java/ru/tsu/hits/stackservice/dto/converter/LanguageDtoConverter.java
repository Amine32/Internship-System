package ru.tsu.hits.stackservice.dto.converter;

import org.springframework.stereotype.Component;
import ru.tsu.hits.stackservice.dto.CreateUpdateLanguageDto;
import ru.tsu.hits.stackservice.dto.LanguageDto;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.model.StackEntity;

import java.util.stream.Collectors;

@Component
public class LanguageDtoConverter {

    public static LanguageEntity dtoToEntity(CreateUpdateLanguageDto dto) {
        LanguageEntity entity = new LanguageEntity();
        entity.setName(dto.getName());
        return entity;
    }

    public static LanguageDto entityToDto(LanguageEntity entity) {
        LanguageDto dto = new LanguageDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRelatedStackIds(entity.getRelatedStacks().stream()
                .map(StackEntity::getId)
                .collect(Collectors.toList()));

        return dto;
    }
}
