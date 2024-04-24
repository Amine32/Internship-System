package ru.tsu.hits.season_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateUpdateInternshipSeasonDto {
    private String title;
    private String phase;
    private Set<String> studentIds;
}
