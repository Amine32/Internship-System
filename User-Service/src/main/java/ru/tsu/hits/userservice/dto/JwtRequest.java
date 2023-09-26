package ru.tsu.hits.userservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequest {
    private String email;
    private String password;
}
