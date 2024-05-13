package ru.tsu.hits.application_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "applications")
public class ApplicationEntity {

    @Id
    private String id;

    private String positionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<StatusHistory> statusHistory;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<InterviewEntity> interviews;

    private int priority;
}
