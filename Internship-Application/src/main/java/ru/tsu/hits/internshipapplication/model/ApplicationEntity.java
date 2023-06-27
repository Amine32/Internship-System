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

    @ElementCollection(targetClass = Status.class)
    @CollectionTable(name = "status", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private List<Status> status;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<InterviewEntity> interviews;
}
