package ru.tsu.hits.companyservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CompanyDto {

    private String id;
    private String name;
    private String description;
    private String address;
    private String contacts;
    private String websiteURL;
    private String logoURL;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PositionDto> positions;
}

