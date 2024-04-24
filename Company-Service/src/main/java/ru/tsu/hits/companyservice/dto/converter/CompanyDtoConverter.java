package ru.tsu.hits.companyservice.dto.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tsu.hits.companyservice.dto.CompanyDto;
import ru.tsu.hits.companyservice.dto.CreateUpdateCompanyDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.model.CompanyEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();
    private final PositionDtoConverter positionDtoConverter;

    public CompanyDto convertToDto(CompanyEntity entity) {
        CompanyDto dto = modelMapper.map(entity, CompanyDto.class);

        if (entity.getPositions() != null) {
            // Convert each PositionEntity to PositionDto
            List<PositionDto> positionDtos = entity.getPositions().stream()
                    .map(positionDtoConverter::convertToDto)
                    .collect(Collectors.toList());

            dto.setPositions(positionDtos);
        } else {
            dto.setPositions(Collections.emptyList());
        }

        return dto;
    }

    public CompanyEntity convertToEntity(CreateUpdateCompanyDto dto) {
        return modelMapper.map(dto, CompanyEntity.class);
    }
}

