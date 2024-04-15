package ru.tsu.hits.applicationservice.model;

import lombok.Getter;

@Getter
public enum Status {
    NEW("Подана заявка"),
    REJECTED("В оффере отказано"),
    INTERVIEW_IS_APPOINTED("Назначено собеседование"),
    INTERVIEW_PASSED("Пройдено собеседование"),
    WAITING_FOR_COMPANY_ANSWER("Ожидается ответ от компании после прохождения собеседования"),
    WAITING_FOR_STUDENT_ANSWER("Ожидается ответ от студента"),
    OFFER_ISSUED("Предложен оффер"),
    OFFER_ACCEPTED("Принят оффер"),
    OFFER_REJECTED("Не принят оффер");

    private final String russian;

    Status(String russian) {
        this.russian = russian;
    }

}
