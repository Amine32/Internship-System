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
    private Long languageId;
    private Long stackId;
    private List<Long> technologiesIds;
}

