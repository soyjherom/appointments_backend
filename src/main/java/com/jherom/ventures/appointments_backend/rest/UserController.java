package com.jherom.ventures.appointments_backend.rest;

import com.jherom.ventures.appointments_backend.domain.outbounds.UserService;
import com.jherom.ventures.appointments_backend.exceptions.CommonException;
import com.jherom.ventures.appointments_backend.rest.resources.inbound.UserRequest;
import com.jherom.ventures.appointments_backend.rest.resources.outbound.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user",
            description = "Enables the user creation")
    @ApiResponses(value = {
            @ApiResponse(description = "User created successfully", responseCode = "201"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@Valid @RequestBody UserRequest userRequest) throws CommonException {
        return userService.createUser(userRequest);
    }

    @Operation(summary = "Update an existent user",
            description = "Enables the user information update")
    @ApiResponses(value = {
            @ApiResponse(description = "User updated successfully", responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId,
                                   @Valid @RequestBody UserRequest userRequest) throws CommonException {
        return userService.updateUser(userId, userRequest);
    }

    @Operation(summary = "Get user by ID",
            description = "Retrieves user specific information by ID")
    @ApiResponses(value = {
            @ApiResponse(description = "User retrieved successfully", responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable String userId) throws CommonException {
        return userService.getUserById(userId);
    }

    @Operation(summary = "Get all users",
            description = "Retrieves all user information")
    @ApiResponses(value = {
            @ApiResponse(description = "Users retrieved successfully", responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public Page<UserResponse> getAllUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDirection) {
        return userService.getAllUsers(page, size, sortBy, sortDirection);
    }

    @Operation(summary = "Get all users by name",
            description = "Retrieves all user information that coincides with a name")
    @ApiResponses(value = {
            @ApiResponse(description = "Users retrieved successfully", responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public Page<UserResponse> getUsersByName(
            @RequestParam String name,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam String sortDirection) {
        return userService.getUsersByName(name, page, size, sortBy, sortDirection);
    }

    @Operation(summary = "Delete an existent user",
            description = "Enables the user information deletion")
    @ApiResponses(value = {
            @ApiResponse(description = "User deleted successfully", responseCode = "204"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable String userId) throws CommonException {
        userService.deleteUserById(userId);
    }
}
