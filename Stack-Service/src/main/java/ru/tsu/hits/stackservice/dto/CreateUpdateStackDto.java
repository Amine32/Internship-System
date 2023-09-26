package ru.tsu.hits.stackservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateUpdateStackDto {

    private String name;

    private List<Long> relatedLanguageIds;

    private List<Long> relatedTechnologyIds;
}
