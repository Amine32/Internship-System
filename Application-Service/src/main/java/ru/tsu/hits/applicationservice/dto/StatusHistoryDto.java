package ru.tsu.hits.applicationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StatusHistoryDto {

    private String id;

    private String applicationId;

    private String status;

    private LocalDate addedAt;
}
