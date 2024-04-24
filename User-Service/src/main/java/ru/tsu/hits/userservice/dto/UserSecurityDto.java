package ru.tsu.hits.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserSecurityDto {

    private String email;

    private String password;

    private Set<String> roles;
}
