package ru.tsu.hits.internshipapplication.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "applications")
public class ApplicationEntity {

    @Id
    private String id;

    private String positionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentProfile student;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<StatusHistory> statusHistory;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<InterviewEntity> interviews;

    private int priority;
}
