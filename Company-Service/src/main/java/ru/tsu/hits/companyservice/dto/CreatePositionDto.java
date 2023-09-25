package ru.tsu.hits.companyservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreatePositionDto {
    private String title;
    private String description;
    private int numberOfPlaces;
    private String salaryRange;
    private String companyId;
    private String languageId;
    private String stackId;
    private List<String> technologiesIds;
}

