package ru.tsu.hits.stackservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class StackDto {

    private Long id;

    private String name;

    private List<Long> relatedLanguageIds;

    private List<Long> relatedTechnologyIds;
}
