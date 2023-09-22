package ru.tsu.hits.internshipapplication.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "students")
public class StudentProfile {

    //student id and user id will be the same
    @Id
    private String id;

    @Lob
    private byte[] resume;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<ApplicationEntity> applications;

    @ElementCollection
    private List<String> languageIds;

    @ElementCollection
    private List<String> positionIds;

    @ElementCollection
    private List<String> technologyIds;
}
