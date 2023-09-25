package ru.tsu.hits.internshipapplication.model;

import lombok.Getter;

@Getter
public enum Status {
    NEW("Подана заявка"),
    REJECTED("Отказано"),
    INTERVIEW_IS_APPOINTED("Назначено собеседование"),
    INTERVIEW_PASSED("Пройдено собеседование"),
    OFFER_ISSUED("Предложен оффер"),
    OFFER_ACCEPTED("Принят оффер"),
    OFFER_REJECTED("Не принят оффер");

    private final String russian;

    Status(String russian) {
        this.russian = russian;
    }

}
