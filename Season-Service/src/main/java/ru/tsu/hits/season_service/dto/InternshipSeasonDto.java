package ru.tsu.hits.season_service.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternshipSeasonDto {
    private String id;
    private String title;
    private String phase;
    private JsonNode students;
}

