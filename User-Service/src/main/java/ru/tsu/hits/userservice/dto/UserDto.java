package ru.tsu.hits.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String userId;

    private String firstName;

    private String lastName;

    private String patronym;

    private String role;

    private String email;

    private String groupNumber;

    private String companyId;
}
