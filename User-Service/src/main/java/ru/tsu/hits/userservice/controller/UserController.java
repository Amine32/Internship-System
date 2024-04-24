package ru.tsu.hits.userservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.userservice.dto.CreateUserDto;
import ru.tsu.hits.userservice.dto.UpdateUserDto;
import ru.tsu.hits.userservice.dto.UserDto;
import ru.tsu.hits.userservice.dto.UserSecurityDto;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.service.UserCommandService;
import ru.tsu.hits.userservice.service.UserQueryService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PostMapping("/sign-up")
    public UserDto signUp(@Valid @RequestBody CreateUserDto user) {
        return userCommandService.signUp(user);
    }

    @GetMapping("/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        return userQueryService.getUserDtoByEmail(email);
    }

    @GetMapping("/roles/{role}")
    public Page<UserDto> getUsersByRole(@PathVariable String role,
                                        @RequestParam Optional<Integer> page,
                                        @RequestParam Optional<Integer> size,
                                        @RequestParam Optional<String> sort) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(25), Sort.by(sort.orElse("id")));
        return userQueryService.getUsersByRole(Role.valueOf(role), pageable);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable String id) {
        userCommandService.deleteUser(id);
    }

    @GetMapping("/security/{email}")
    public UserSecurityDto getUserSecurityDtoByEmail(@PathVariable String email) {
        return userCommandService.getUserSecurityDetails(email);
    }

    @PostMapping("/company/{userId}/{companyId}")
    public UserDto setCompany(@PathVariable String userId, @PathVariable String companyId) {
        return userCommandService.addCompany(userId, companyId);
    }

    @GetMapping("/id/{userId}")
    public UserDto getUserById(@PathVariable String userId) {
        return userQueryService.getUserDtoById(userId);
    }

    @PatchMapping("/edit/{userId}")
    public UserDto editUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDto dto) {
        return userCommandService.editUserById(userId, dto);
    }
}
