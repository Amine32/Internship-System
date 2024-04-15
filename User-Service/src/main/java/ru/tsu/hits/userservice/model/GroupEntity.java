package ru.tsu.hits.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class GroupEntity {

    @Id
    private String groupNumber;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<UserEntity> students;
}
