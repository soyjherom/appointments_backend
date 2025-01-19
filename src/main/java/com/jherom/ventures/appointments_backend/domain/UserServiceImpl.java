package com.jherom.ventures.appointments_backend.domain;

import com.jherom.ventures.appointments_backend.domain.mappers.UserMapper;
import com.jherom.ventures.appointments_backend.domain.outbounds.UserService;
import com.jherom.ventures.appointments_backend.exceptions.CommonException;
import com.jherom.ventures.appointments_backend.exceptions.UserNotFoundException;
import com.jherom.ventures.appointments_backend.models.User;
import com.jherom.ventures.appointments_backend.repositories.UserRepository;
import com.jherom.ventures.appointments_backend.rest.resources.inbound.UserRequest;
import com.jherom.ventures.appointments_backend.rest.resources.outbound.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public String createUser(UserRequest userRequest) {
        User user = userMapper.userRequestToUser(userRequest);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public UserResponse updateUser(String userId, UserRequest userRequest) throws CommonException {
        User currentUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User updatingUser = userMapper.userRequestToUser(userId, userRequest);
        if (thereAreChanges(currentUser, updatingUser)) {
            User updatedUser = userRepository.save(updatingUser);
            return userMapper.userToUserResponse(updatedUser);
        }
        return userMapper.userToUserResponse(currentUser);
    }

    private boolean thereAreChanges(User currentUser, User updatingUser) {
        if (currentUser.getName().equals(updatingUser.getName()) &&
        currentUser.getEmail().equals(updatingUser.getEmail()))
    }

    @Override
    public UserResponse getUserById(String userId) {
        return null;
    }

    @Override
    public Page<UserResponse> getUsersByName(String name, int page, int size, String sortBy, String sortDirection) {
        return null;
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDirection) {
        return null;
    }

    @Override
    public void deleteUserById(String userId) {

    }
}
