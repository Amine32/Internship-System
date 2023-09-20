package ru.tsu.hits.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.userservice.dto.CreateUserDto;
import ru.tsu.hits.userservice.dto.UpdateUserDto;
import ru.tsu.hits.userservice.dto.UserDto;
import ru.tsu.hits.userservice.dto.UserSecurityDto;
import ru.tsu.hits.userservice.dto.converter.UserDtoConverter;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.model.UserEntity;
import ru.tsu.hits.userservice.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final UserNotificationService userNotificationService;
    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signUp(CreateUserDto dto) {
        UserEntity userEntity = createUserEntity(dto);
        userValidationService.validateNewUser(userEntity);
        return saveAndNotify(userEntity);
    }

    @Transactional
    public UserDto editUserById(String userId, UpdateUserDto dto) {
        UserEntity user = userQueryService.getUserById(userId);
        userValidationService.validateUserForEdit(user, dto);
        updateUserFields(user, dto);
        user = userRepository.save(user);

        return UserDtoConverter.convertEntityToDto(user);
    }

    @Transactional
    public UserDto addCompany(String userId, String companyId) {
        UserEntity user = userQueryService.getUserById(userId);
        userValidationService.validateUserForCompanyAddition(user);
        user.setCompanyId(companyId);
        user = userRepository.save(user);
        return UserDtoConverter.convertEntityToDto(user);
    }

    @Transactional
    public void deleteUser(String id, HttpServletRequest request) {
        userValidationService.validateUserForDeletion(id);
        userRepository.deleteById(id);
        userNotificationService.notifyDeletion(id, request);
    }

    public void editUser(UserEntity user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserSecurityDto getUserSecurityDetails(String email) {
        UserEntity user = userQueryService.getUserByEmail(email);

        UserSecurityDto dto = new UserSecurityDto();
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole().name());

        return dto;
    }

    private UserEntity createUserEntity(CreateUserDto dto) {
        UserEntity userEntity = UserDtoConverter.convertDtoToEntity(dto);
        userEntity.setRole(Role.valueOf(dto.getRole()));
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userEntity;
    }

    private UserDto saveAndNotify(UserEntity userEntity) {
        userEntity = userRepository.save(userEntity);
        if (userEntity.getRole() == Role.STUDENT) {
            userNotificationService.notifyStudentService(userEntity);
        }
        return UserDtoConverter.convertEntityToDto(userEntity);
    }

    private void updateUserFields(UserEntity user, UpdateUserDto dto) {
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPatronym(dto.getPatronym());
    }
}