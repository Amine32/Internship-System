package ru.tsu.hits.document_service.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomPrincipal {
    private String username;
    private String userId;
}
