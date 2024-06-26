package ru.tsu.hits.companyservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PositionDto {

    private String id;
    private String title;
    private String description;
    private int numberOfPlaces;
    private int numberOfPlacesLeft;
    private String salaryRange;
    private String status;
    private int numberOfApplications;
    private String stack;
    private String language;
    private List<String> technologies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String companyName;
    private String companyId;
    private String searchPhaseId;
}

