package ru.tsu.hits.companyservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class UpdatePositionDto {
    private String title;
    private String description;
    private List<String> requirements;
    private int numberOfPlaces;
    private String salaryRange;
}

