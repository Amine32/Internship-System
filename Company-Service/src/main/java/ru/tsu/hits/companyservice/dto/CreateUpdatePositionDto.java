package ru.tsu.hits.companyservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateUpdatePositionDto {

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @PositiveOrZero(message = "Number of places should be zero or positive")
    private int numberOfPlaces;

    @Pattern(regexp = "(0|[1-9][0-9]*|[0-9]+-[0-9]+)", message = "Invalid salary range")
    private String salaryRange;

    @NotNull(message = "Company ID cannot be null")
    private String companyId;

    private Long languageId;

    private Long stackId;

    private List<Long> technologiesIds;
}

