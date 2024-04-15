package ru.tsu.hits.stackservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LanguageDto {

    private Long id;

    private String name;

    private List<Long> relatedStackIds;

    private List<Long> relatedTechnologyIds;
}
