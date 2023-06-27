package ru.tsu.hits.internshipapplication.dto;

import lombok.Data;

@Data
public class UserSecurityDto {

    private String email;

    private String password;

    private String role;
}
