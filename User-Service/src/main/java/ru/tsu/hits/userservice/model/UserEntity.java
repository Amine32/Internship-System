package ru.tsu.hits.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    private String id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String patronym;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    private String companyId;
}
