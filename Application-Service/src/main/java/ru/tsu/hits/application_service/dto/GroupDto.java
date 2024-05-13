package ru.tsu.hits.application_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupDto {

    private String groupNumber;

    private List<StudentDto> students;
}
