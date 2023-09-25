package ru.tsu.hits.companyservice.model;

import lombok.Getter;

@Getter
public enum PositionStatus {
    OPEN("Open"),
    CLOSED("Closed"),
    FILLED("Filled");

    private final String displayValue;

    PositionStatus(String displayValue) {
        this.displayValue = displayValue;
    }
}
