package ru.tsu.hits.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsu.hits.userservice.dto.CreateUserDto;
import ru.tsu.hits.userservice.dto.UpdateUserDto;
import ru.tsu.hits.userservice.exception.TokenNotFoundException;
import ru.tsu.hits.userservice.exception.UserLacksFieldException;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.model.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Map;

@Service
public class ValidationService {

    @Autowired
    private UserService userService;

    public void validateUserSignUp(CreateUserDto dto) {
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new UserLacksFieldException("Email field cannot be empty");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new UserLacksFieldException("Password field cannot be empty");
        }
        if (dto.getRole() == null) {
            throw new UserLacksFieldException("Role must be defined");
        }
        if (dto.getRole().equals(Role.STUDENT.toString()) && (dto.getGroupNumber() == null || dto.getGroupNumber().isEmpty())) {
            throw new UserLacksFieldException("Student role requires a group number");
        }
        if (dto.getRole().equals(Role.COMPANY.toString()) && (dto.getCompanyId() == null || dto.getCompanyId().isEmpty())) {
            throw new UserLacksFieldException("Company role requires a company id");
        }
    }

    public void validateUserEdit(UpdateUserDto dto, UserEntity existingUser) {
        if (dto.getEmail() != null && !dto.getEmail().equals(existingUser.getEmail()) && userService.getUserByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Further validations can be added as per requirement
    }

    public UserEntity extractUserFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new TokenNotFoundException("Authorization header must be provided");
        }
        String token = authHeader.substring(7);
        String[] chunks = token.split("\.");
        if (chunks.length < 2) {
            throw new TokenNotFoundException("Invalid token");
        }
        String payload = new String(Base64.getDecoder().decode(chunks[1]));
        Map<String, Object> payloadMap;
        try {
            payloadMap = new ObjectMapper().readValue(payload, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to decode payload", e);
        }
        String email = (String) payloadMap.get("sub");
        return userService.getUserByEmail(email);
    }
}
