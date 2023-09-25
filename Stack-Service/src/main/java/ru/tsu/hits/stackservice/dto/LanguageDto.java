package ru.tsu.hits.stackservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class LanguageDto {

    private Long id;

    private String name;

    private List<Long> relatedStackIds;
}
