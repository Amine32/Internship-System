package ru.tsu.hits.applicationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateUpdateInterviewDto {

    private LocalDateTime date;

    private String location;
}
