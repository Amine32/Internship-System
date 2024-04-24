package ru.tsu.hits.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.userservice.dto.UserDto;
import ru.tsu.hits.userservice.dto.converter.UserDtoConverter;
import ru.tsu.hits.userservice.exception.UserNotFoundException;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.model.UserEntity;
import ru.tsu.hits.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserEntity getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
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
        return userRepository.findAllByRolesContains(role, pageable).map(UserDtoConverter::convertEntityToDto);
    }
}
