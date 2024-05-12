package ru.tsu.hits.practice_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "internship_evaluations")
public class InternshipEvaluation {

    @Id
    @UuidGenerator
    private String id;

    @OneToOne
    @JoinColumn(name = "semestral_report_id")
    private SemestralReport semestralReport;

    private Float grade;
    private String feedback;
    private LocalDateTime evaluatedAt;
}
