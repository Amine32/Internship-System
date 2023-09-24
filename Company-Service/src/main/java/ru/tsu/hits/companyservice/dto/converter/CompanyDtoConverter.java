package ru.tsu.hits.companyservice.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.tsu.hits.companyservice.dto.CompanyDto;
import ru.tsu.hits.companyservice.dto.CreateUpdateCompanyDto;
import ru.tsu.hits.companyservice.model.CompanyEntity;

@Component
public class CompanyDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public CompanyDto convertToDto(CompanyEntity entity) {
        return modelMapper.map(entity, CompanyDto.class);
    }

    public CompanyEntity convertToEntity(CreateUpdateCompanyDto dto) {
        return modelMapper.map(dto, CompanyEntity.class);
    }
}

