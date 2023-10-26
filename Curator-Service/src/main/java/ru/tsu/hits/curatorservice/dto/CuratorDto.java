package ru.tsu.hits.curatorservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CuratorDto {
    private String id;
    private List<CompanyDto> companies;  // Map of Company ID to Company Name
}
