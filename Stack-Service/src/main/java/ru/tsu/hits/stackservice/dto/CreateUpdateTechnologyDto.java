package ru.tsu.hits.stackservice.dto;

import lombok.Data;

@Data
public class CreateUpdateTechnologyDto {

    private String name;

    private Long relatedStackId;

    private Long relatedLanguageId;
}
