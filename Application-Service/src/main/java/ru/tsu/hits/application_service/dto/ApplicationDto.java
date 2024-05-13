package ru.tsu.hits.application_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationDto {

    private String id;

    private String positionId;

    private String studentId;

    private List<StatusHistoryDto> statusHistory;

    private List<InterviewDto> interviews;

    private String position;

    private String companyName;

    private String companyId;

    private int priority;
}
