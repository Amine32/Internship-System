package ru.tsu.hits.internshipapplication.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProcessedEvent {

    @Id
    private String id;

    public ProcessedEvent(String id) {
        this.id = id;
    }

    public ProcessedEvent() {

    }
}
