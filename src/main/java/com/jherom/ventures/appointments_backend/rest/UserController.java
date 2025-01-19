package com.jherom.ventures.appointments_backend.rest;

import com.jherom.ventures.appointments_backend.domain.outbounds.UserService;
import com.jherom.ventures.appointments_backend.exceptions.CommonException;
import com.jherom.ventures.appointments_backend.rest.resources.inbound.UserRequest;
import com.jherom.ventures.appointments_backend.rest.resources.outbound.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@Valid @RequestBody UserRequest userRequest) throws CommonException {
        return userService.createUser(userRequest);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @Valid @RequestBody UserRequest userRequest) throws CommonException {
        return userService.updateUser(userId, userRequest);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable String userId) throws CommonException {
        return userService.getUserById(userId);
    }

    @GetMapping
    public Page<UserResponse> getAllUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDirection) {
        return userService.getAllUsers(page, size, sortBy, sortDirection);
    }

    @GetMapping("/search")
    public Page<UserResponse> getUsersByName(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDirection) {
        return userService.getUsersByName(name, page, size, sortBy, sortDirection);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable String userId) throws CommonException {
        userService.deleteUserById(userId);
    }
}
