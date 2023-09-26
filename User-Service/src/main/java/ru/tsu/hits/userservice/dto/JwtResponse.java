package ru.tsu.hits.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String jwtToken;
}
