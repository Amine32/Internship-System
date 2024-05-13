package ru.tsu.hits.application_service.dto.converter;

import org.modelmapper.ModelMapper;
import ru.tsu.hits.application_service.dto.CreateUpdateInterviewDto;
import ru.tsu.hits.application_service.dto.InterviewDto;
import ru.tsu.hits.application_service.model.InterviewEntity;

public class InterviewDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static InterviewEntity convertDtoToEntity(CreateUpdateInterviewDto dto) {
        return modelMapper.map(dto, InterviewEntity.class);
    }

    public static InterviewDto convertEntityToDto(InterviewEntity interviewEntity) {
        return modelMapper.map(interviewEntity, InterviewDto.class);
    }
}
