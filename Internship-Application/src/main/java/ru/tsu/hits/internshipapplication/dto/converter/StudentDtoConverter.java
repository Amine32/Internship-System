package ru.tsu.hits.internshipapplication.dto.converter;

import org.modelmapper.ModelMapper;
import ru.tsu.hits.internshipapplication.dto.StudentDto;
import ru.tsu.hits.internshipapplication.model.StudentProfile;

public class StudentDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static StudentDto convertEntityToDto(StudentProfile student) {
        return modelMapper.map(student, StudentDto.class);
    }
}
