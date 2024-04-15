package ru.tsu.hits.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSecurityDto {

    private String email;

    private String password;

    private String role;
}
