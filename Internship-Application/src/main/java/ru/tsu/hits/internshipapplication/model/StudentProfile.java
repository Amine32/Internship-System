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

    @OneToMany(mappedBy = "student")
    private List<ApplicationEntity> applications;

    @ElementCollection
    private List<Long> languageIds;

    @ElementCollection
    private List<Long> StackIds;

    @ElementCollection
    private List<Long> technologyIds;
}
