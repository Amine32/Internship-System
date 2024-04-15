package ru.tsu.hits.applicationservice.dto.converter;

import org.modelmapper.ModelMapper;
import ru.tsu.hits.applicationservice.dto.CreateUpdateInterviewDto;
import ru.tsu.hits.applicationservice.dto.InterviewDto;
import ru.tsu.hits.applicationservice.model.InterviewEntity;

public class InterviewDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static InterviewEntity convertDtoToEntity(CreateUpdateInterviewDto dto) {
        return modelMapper.map(dto, InterviewEntity.class);
    }

    public static InterviewDto convertEntityToDto(InterviewEntity interviewEntity) {
        return modelMapper.map(interviewEntity, InterviewDto.class);
    }
}
