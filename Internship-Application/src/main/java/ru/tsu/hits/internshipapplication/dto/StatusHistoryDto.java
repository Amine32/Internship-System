package ru.tsu.hits.internshipapplication.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StatusHistoryDto {

    private String id;

    private String applicationId;

    private String status;

    private LocalDate addedAt;
}
