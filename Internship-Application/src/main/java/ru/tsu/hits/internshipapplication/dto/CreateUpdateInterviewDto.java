package ru.tsu.hits.internshipapplication.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUpdateInterviewDto {

    private LocalDateTime date;

    private String location;
}
