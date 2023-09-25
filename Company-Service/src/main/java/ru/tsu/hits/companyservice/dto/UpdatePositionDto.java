package ru.tsu.hits.companyservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdatePositionDto {
    private String title;
    private String description;
    private int numberOfPlaces;
    private String salaryRange;
    private String languageId;
    private String stackId;
    private List<String> technologiesIds;
}

