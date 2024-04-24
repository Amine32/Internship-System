package ru.tsu.hits.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


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

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    private String companyId;
}
