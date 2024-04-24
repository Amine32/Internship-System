package ru.tsu.hits.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.userservice.dto.UserDto;
import ru.tsu.hits.userservice.dto.converter.UserDtoConverter;
import ru.tsu.hits.userservice.exception.TokenNotFoundException;
import ru.tsu.hits.userservice.exception.UserNotFoundException;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.model.UserEntity;
import ru.tsu.hits.userservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserEntity getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(String id) {
        return UserDtoConverter.convertEntityToDto(getUserById(id));
    }

    @Transactional(readOnly = true)
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoByEmail(String email) {
        return UserDtoConverter.convertEntityToDto(getUserByEmail(email));
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getUsersByRole(Role role, Pageable pageable) {
        return userRepository.findAllByRolesContains(role, pageable)
                .map(UserDtoConverter::convertEntityToDto);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByToken(HttpServletRequest request) {
        String jwtToken = extractJwtTokenFromRequest(request);
        String email = extractEmailFromJwtToken(jwtToken);
        return getUserDtoByEmail(email);
    }

    private String extractJwtTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            throw new TokenNotFoundException("Token not found");
        }
    }

    private String extractEmailFromJwtToken(String jwtToken) {
        String[] jwtParts = jwtToken.split("\\.");
        String payload = new String(Base64.getDecoder().decode(jwtParts[1]));

        Map<String, Object> payloadMap;
        try {
            payloadMap = new ObjectMapper().readValue(payload, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to decode payload", e);
        }

        return (String) payloadMap.get("sub");
    }
}
