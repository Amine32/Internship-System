package ru.tsu.hits.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateUserDto {

    private String firstName;

    private String lastName;

    private String patronym;

    private Set<String> roles;

    private String email;

    private String password;

    private String groupNumber;

    private String companyId;
}
