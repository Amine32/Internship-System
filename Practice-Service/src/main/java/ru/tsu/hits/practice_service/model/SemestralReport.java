package ru.tsu.hits.practice_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "semestral_reports")
public class SemestralReport {

    @Id
    @UuidGenerator
    private String id;

    @OneToOne
    @JoinColumn(name = "contract_id")
    private InternshipContract contract;

    @Enumerated(EnumType.STRING)
    private Semester semester;

    private LocalDateTime submittedAt;

    @Lob
    private byte[] fullReport;  // Assuming the Word document content is stored as a byte array.

    @OneToOne(mappedBy = "semestralReport")
    private InternshipEvaluation evaluation;
}
