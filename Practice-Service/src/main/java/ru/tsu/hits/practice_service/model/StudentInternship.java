package ru.tsu.hits.practice_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "internships")
public class StudentInternship {

    @Id
    @UuidGenerator
    private String id;

    private String studentId;

    @OneToOne
    @JoinColumn(name = "current_contract_id")
    private InternshipContract currentContract;

    @OneToMany(mappedBy = "studentInternship")
    private List<InternshipContract> history;
}
