package ru.tsu.hits.stackservice.dto;

import lombok.Data;

@Data
public class TechnologyDto {

    private Long id;

    private String name;

    private Long relatedStackId;

    private Long relatedLanguageId;
}
