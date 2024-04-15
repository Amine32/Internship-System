package ru.tsu.hits.stackservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StackDto {

    private Long id;

    private String name;

    private List<Long> relatedLanguageIds;

    private List<Long> relatedTechnologyIds;
}
