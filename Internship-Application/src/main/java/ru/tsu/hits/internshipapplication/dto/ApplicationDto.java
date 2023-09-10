package ru.tsu.hits.internshipapplication.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationDto {

    private String id;

    private String positionId;

    private String studentId;

    private List<String> status;

    private List<InterviewDto> interviews;

    private String position;

    private String companyName;
}
