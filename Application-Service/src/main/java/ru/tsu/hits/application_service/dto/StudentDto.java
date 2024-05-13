package ru.tsu.hits.application_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentDto {

    private String id;

    private String resume;

    private List<ApplicationDto> applications;

    private List<StackDto> stacks;

    private List<StackDto> languages;

    private List<StackDto> technologies;
}
