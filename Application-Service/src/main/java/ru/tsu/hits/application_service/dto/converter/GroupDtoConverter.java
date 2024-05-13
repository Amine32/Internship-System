package ru.tsu.hits.application_service.dto.converter;

import org.modelmapper.ModelMapper;
import ru.tsu.hits.application_service.dto.GroupDto;
import ru.tsu.hits.application_service.dto.StudentDto;
import ru.tsu.hits.application_service.model.GroupEntity;

import java.util.ArrayList;
import java.util.List;

public class GroupDtoConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static GroupDto convertEntityToDto(GroupEntity group) {
        GroupDto dto = modelMapper.map(group, GroupDto.class);

        List<StudentDto> students = new ArrayList<>();

        if (group.getStudents() != null) {
            group.getStudents().forEach(element -> students.add(StudentDtoConverter.convertEntityToDto(element)));
        }

        dto.setStudents(students);

        return dto;
    }
}
