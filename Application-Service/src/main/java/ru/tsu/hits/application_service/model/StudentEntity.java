package ru.tsu.hits.application_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "students")
public class StudentEntity {

    //student id and user id will be the same
    @Id
    private String id;

    @ManyToOne
    private GroupEntity group;

    @Lob
    private byte[] resume;

    @OneToMany(mappedBy = "student")
    private List<ApplicationEntity> applications;

    @ElementCollection
    private List<Long> languageIds;

    @ElementCollection
    private List<Long> StackIds;

    @ElementCollection
    private List<Long> technologyIds;
}
