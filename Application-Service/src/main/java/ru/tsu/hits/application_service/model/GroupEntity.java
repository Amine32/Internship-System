package ru.tsu.hits.application_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class GroupEntity {

    @Id
    private String groupNumber;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<StudentEntity> students;
}
