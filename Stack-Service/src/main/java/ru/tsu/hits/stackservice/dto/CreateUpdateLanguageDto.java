package ru.tsu.hits.stackservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateUpdateLanguageDto {

    private String name;

    private List<Long> relatedStackIds;

    private List<Long> relatedTechnologyIds;
}
