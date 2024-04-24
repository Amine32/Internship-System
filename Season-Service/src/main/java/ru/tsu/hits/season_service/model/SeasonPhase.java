package ru.tsu.hits.season_service.model;

import lombok.Getter;

@Getter
public enum SeasonPhase {
    SEARCH("Фаза Поиска"),
    PRACTICE("Фаза Практики"),
    COMPLETED("Сезон Совершена"),;

    private final String displayValue;

    SeasonPhase(String displayValue) {
        this.displayValue = displayValue;
    }
}
