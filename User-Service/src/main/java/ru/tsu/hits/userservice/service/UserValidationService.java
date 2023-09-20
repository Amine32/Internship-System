package ru.tsu.hits.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.userservice.dto.UpdateUserDto;
import ru.tsu.hits.userservice.exception.UserNotFoundException;
import ru.tsu.hits.userservice.exception.WrongRoleException;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.model.UserEntity;
import ru.tsu.hits.userservice.repository.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;

    public void validateNewUser(UserEntity user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
    }

    public void validateUserForEdit(UserEntity existingUser, UpdateUserDto dto) {
        UserEntity checkEmail = userRepository.findByEmail(dto.getEmail());
        if(!Objects.equals(existingUser.getEmail(), dto.getEmail()) && checkEmail != null) {
            throw new  IllegalArgumentException("Email already exists");
        }
    }

    public void validateUserForDeletion(String id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    public void validateUserForCompanyAddition(UserEntity user) {
        if (user.getRole() != Role.COMPANY) {
            throw new WrongRoleException("User does not have COMPANY role");
        }
    }
}
