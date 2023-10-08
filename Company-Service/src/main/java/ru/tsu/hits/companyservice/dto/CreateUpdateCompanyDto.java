package ru.tsu.hits.companyservice.dto;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.*;

@Data
public class CreateUpdateCompanyDto {
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 255)
    private String address;

    @Size(max = 100)
    private String contacts;

    @URL(message = "Invalid URL format")
    private String websiteURL;

    @Pattern(regexp = "([a-zA-Z0-9\\s_\\\\.\\-:/])+(.png|.jpg|.gif)$", message = "Invalid logo URL")
    private String logoURL;
}

