package ru.tsu.hits.season_service.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PracticePeriodDto {
    private String id;
    private String seasonId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
    private JsonNode contracts;
}

