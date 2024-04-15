package ru.tsu.hits.stackservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateUpdateLanguageDto {

    private String name;

    private List<Long> relatedStackIds;

    private List<Long> relatedTechnologyIds;
}
