package ru.tsu.hits.companyservice.dto;

import lombok.Data;

@Data
public class CreateUpdateCompanyDto {
    private String name;
    private String description;
    private String address;
    private String contacts;
    private String websiteURL;
    private String logoURL;
}

