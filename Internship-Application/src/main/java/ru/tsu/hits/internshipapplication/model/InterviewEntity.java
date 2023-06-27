package ru.tsu.hits.internshipapplication.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
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
