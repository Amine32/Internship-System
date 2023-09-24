package ru.tsu.hits.companyservice.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tsu.hits.companyservice.dto.CreatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.model.PositionEntity;

@Component
public class PositionDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public PositionDto convertToDto(PositionEntity entity) {
        return modelMapper.map(entity, PositionDto.class);
    }

    public PositionEntity convertToEntity(CreatePositionDto dto) {
        return modelMapper.map(dto, PositionEntity.class);
    }
}
