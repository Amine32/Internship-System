package ru.tsu.hits.userservice.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.userservice.dto.CreateUserDto;
import ru.tsu.hits.userservice.dto.UpdateUserDto;
import ru.tsu.hits.userservice.dto.UserDto;
import ru.tsu.hits.userservice.dto.UserSecurityDto;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.service.UserCommandService;
import ru.tsu.hits.userservice.service.UserQueryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api("User API")
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
    public List<UserDto> getUsersByRole(@PathVariable String role) {
        return userQueryService.getUsersByRole(Role.valueOf(role));
    }

    @GetMapping("/jwt")
    public UserDto getUserByToken(HttpServletRequest request) {
        return userQueryService.getUserByToken(request);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable String id, HttpServletRequest request) {
        userCommandService.deleteUser(id, request);
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
