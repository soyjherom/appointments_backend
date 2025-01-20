package com.jherom.ventures.appointments_backend.domain;

import com.jherom.ventures.appointments_backend.domain.mappers.UserMapper;
import com.jherom.ventures.appointments_backend.domain.outbounds.UserService;
import com.jherom.ventures.appointments_backend.exceptions.CommonException;
import com.jherom.ventures.appointments_backend.exceptions.HashingException;
import com.jherom.ventures.appointments_backend.exceptions.UserNotFoundException;
import com.jherom.ventures.appointments_backend.models.User;
import com.jherom.ventures.appointments_backend.repositories.UserRepository;
import com.jherom.ventures.appointments_backend.rest.resources.inbound.UserRequest;
import com.jherom.ventures.appointments_backend.rest.resources.outbound.UserResponse;
import com.jherom.ventures.appointments_backend.utils.CryptoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public String createUser(UserRequest userRequest) throws CommonException {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(userRequest.getName())
                .phone(CryptoUtil.encrypt(userRequest.getPhone()))
                .phoneHash(CryptoUtil.getHash(userRequest.getPhone()))
                .email(CryptoUtil.encrypt(userRequest.getEmail()))
                .emailHash(CryptoUtil.getHash(userRequest.getEmail()))
                .build();
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public UserResponse updateUser(String userId, UserRequest userRequest) throws CommonException {
        User currentUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (!equals(currentUser, userRequest)) {
            currentUser.setName(userRequest.getName());
            currentUser.setPhone(CryptoUtil.encrypt(userRequest.getPhone()));
            currentUser.setPhoneHash(CryptoUtil.getHash(userRequest.getPhone()));
            currentUser.setEmail(CryptoUtil.encrypt(userRequest.getEmail()));
            currentUser.setEmailHash(CryptoUtil.encrypt(userRequest.getEmail()));
            currentUser.setVersion(currentUser.getVersion() + 1);
            User updatedUser = userRepository.save(currentUser);
            return userMapper.userToUserResponse(updatedUser);
        }
        return userMapper.userToUserResponse(currentUser);
    }

    private boolean equals(User currentUser, UserRequest userRequest) throws HashingException {
        final String name = userRequest.getName();
        final String phoneHash = CryptoUtil.getHash(userRequest.getPhone());
        final String emailHash = CryptoUtil.getHash(userRequest.getEmail());
        return currentUser.getName().equals(name)
                && currentUser.getEmailHash().equals(emailHash)
                && currentUser.getPhoneHash().equals(phoneHash);
    }

    @Override
    public UserResponse getUserById(String userId) throws CommonException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public Page<UserResponse> getUsersByName(String name, int page, int size, String sortBy, String sortDirection) {
        return userMapper.pageToUserResponsePage(
                userRepository.findByNameContaining(name,
                        getPageable(page, size, sortBy, sortDirection))
        );
    }

    private Pageable getPageable(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        return PageRequest.of(page, size, sort);
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDirection) {
        return userMapper.pageToUserResponsePage(
                userRepository.findAll(getPageable(page, size, sortBy, sortDirection))
        );
    }

    @Override
    public void deleteUserById(String userId) throws CommonException{
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
