package ru.tsu.hits.curatorservice.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomPrincipal {
    private String username;
    private String userId;
}
