package ru.tsu.hits.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.userservice.dto.*;
import ru.tsu.hits.userservice.dto.converter.UserDtoConverter;
import ru.tsu.hits.userservice.exception.*;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.model.UserEntity;
import ru.tsu.hits.userservice.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    @Transactional
    public UserDto signUp(CreateUserDto dto) {
        validationService.validateUserSignUp(dto);

        UserEntity userEntity = UserDtoConverter.convertDtoToEntity(dto);
        userEntity.setRole(Role.valueOf(dto.getRole()));
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        userEntity = userRepository.save(userEntity);

        if(userEntity.getRole() == Role.STUDENT) {
            validationService.validateStudentGroup(userEntity);
        }

        if(userEntity.getRole() == Role.COMPANY) {
            validationService.validateCompanyUser(userEntity);
        }

        return UserDtoConverter.convertEntityToDto(userEntity);
    }

    public void editUser(UserEntity user) {
        userRepository.save(user);
    }

    @Transactional
    public UserDto editUserById(String userId, UpdateUserDto dto) {
        UserEntity user = getUserById(userId);

        validationService.validateUserEdit(dto, user);

        editUser(user);

        return UserDtoConverter.convertEntityToDto(user);
    }

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
    public UserDto getUserDtoByEmail(String email) {
        return UserDtoConverter.convertEntityToDto(getUserByEmail(email));
    }

    @Transactional(readOnly = true)
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsersByRole(Role role) {
        List<UserEntity> users = userRepository.findAllByRole(role);
        List<UserDto> result = new ArrayList<>();

        users.forEach(element -> result.add(UserDtoConverter.convertEntityToDto(element)));

        return result;
    }

    @Transactional
    public void deleteUser(String id, HttpServletRequest request) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto addCompany(String userId, String companyId) {
        UserEntity user = getUserById(userId);

        if(user.getRole() != Role.COMPANY) {
            throw new WrongRoleException("User does not have COMPANY role");
        }

        user.setCompanyId(companyId);
        user = userRepository.save(user);

        return UserDtoConverter.convertEntityToDto(user);
    }

    @Transactional
    public UserSecurityDto getUserSecurityDetails(String email) {
        UserEntity user = getUserByEmail(email);

        UserSecurityDto dto = new UserSecurityDto();
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole().name());

        return dto;
    }

    @Transactional(readOnly = true)
    public UserDto getUserByToken(HttpServletRequest request) {
        return getUserDtoByEmail(validationService.extractEmailFromToken(request));
    }

}
