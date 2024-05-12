package ru.tsu.hits.practice_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "internship_contracts")
public class InternshipContract {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_internship_id")
    private StudentInternship studentInternship;

    private String companyId;
    private String positionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String terms;
    @Enumerated(EnumType.STRING)
    private ContractStatus status;
    private boolean signedByStudent;
    private boolean signedByCompany;
    private boolean approvedByDean;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "internshipContract")
    private SemestralReport semestralReport;

    private String curatorId;
}
