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

import java.util.Set;
import java.util.stream.Collectors;

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
    public void deleteUser(String id) {
        userValidationService.validateUserForDeletion(id);
        UserEntity user = userQueryService.getUserById(id);  // Fetch user entity to check role before deletion
        userRepository.deleteById(id);
        if (user.getRoles().contains(Role.STUDENT)) {
            userNotificationService.notifyStudentDeletion(id);
        } else if (user.getRoles().contains(Role.CURATOR)) {
            userNotificationService.notifyCuratorDeletion(id);
        }
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
        dto.setRoles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));

        return dto;
    }

    private UserEntity createUserEntity(CreateUserDto dto) {
        UserEntity userEntity = UserDtoConverter.convertDtoToEntity(dto);
        Set<Role> roles = dto.getRoles().stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        userEntity.setRoles(roles);
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userEntity;
    }

    private UserDto saveAndNotify(UserEntity userEntity) {
        userEntity = userRepository.save(userEntity);
        if (userEntity.getRoles().contains(Role.STUDENT)) {
            userNotificationService.notifyStudentService(userEntity);
        } else if (userEntity.getRoles().contains(Role.CURATOR)) {
            userNotificationService.notifyCuratorService(userEntity);
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