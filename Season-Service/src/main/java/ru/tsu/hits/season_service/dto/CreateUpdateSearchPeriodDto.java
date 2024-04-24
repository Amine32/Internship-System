package ru.tsu.hits.season_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUpdateSearchPeriodDto {
    private String seasonId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
}
