package ru.tsu.hits.companyservice.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PositionDto {

    private String id;
    private String title;
    private String description;
    private List<String> requirements;
    private int numberOfPlaces;
    private int numberOfPlacesLeft;
    private String salaryRange;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

