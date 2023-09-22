package ru.tsu.hits.internshipapplication.dto.converter;

import org.modelmapper.ModelMapper;
import ru.tsu.hits.internshipapplication.dto.StatusHistoryDto;
import ru.tsu.hits.internshipapplication.model.StatusHistory;

public class StatusHistoryDtoConverter {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static StatusHistoryDto convertEntityToDto(StatusHistory entity) {
        StatusHistoryDto dto = modelMapper.map(entity, StatusHistoryDto.class);
        dto.setStatus(entity.getStatus().getRussian());
        return dto;
    }
}
