package ru.tsu.hits.applicationservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "interviews")
public class InterviewEntity {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;

    private LocalDateTime date;

    private String location;
}
