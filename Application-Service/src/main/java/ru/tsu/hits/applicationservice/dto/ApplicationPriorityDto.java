package ru.tsu.hits.applicationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationPriorityDto {
    private String applicationId;
    private int priority;
}
