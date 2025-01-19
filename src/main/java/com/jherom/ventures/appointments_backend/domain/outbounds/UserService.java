package com.jherom.ventures.appointments_backend.domain.outbounds;

import com.jherom.ventures.appointments_backend.exceptions.CommonException;
import com.jherom.ventures.appointments_backend.exceptions.UserNotFoundException;
import com.jherom.ventures.appointments_backend.rest.resources.inbound.UserRequest;
import com.jherom.ventures.appointments_backend.rest.resources.outbound.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    String createUser(UserRequest userRequest) throws CommonException;
    UserResponse updateUser(String userId, UserRequest userRequest) throws CommonException;
    UserResponse getUserById(String userId);
    Page<UserResponse> getUsersByName(String name, int page, int size, String sortBy, String sortDirection);
    Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDirection);
    void deleteUserById(String userId);
}
