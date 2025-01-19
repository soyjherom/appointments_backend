package com.jherom.ventures.appointments_backend.domain.mappers;

import com.jherom.ventures.appointments_backend.models.User;
import com.jherom.ventures.appointments_backend.rest.resources.inbound.UserRequest;
import com.jherom.ventures.appointments_backend.rest.resources.outbound.UserResponse;
import com.jherom.ventures.appointments_backend.utils.CryptoUtil;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserResponse userToUserResponse(User user);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "phone", expression = "java(CryptoUtil.encryptAES(userRequest.getPhone()))")
    @Mapping(target = "email", expression = "java(CryptoUtil.encryptAES(userRequest.getEmail()))")
    @Mapping(target = "phoneHash", expression = "java(CryptoUtil.generateHash(userRequest.getPhone()))")
    @Mapping(target = "emailHash", expression = "java(CryptoUtil.generateHash(userRequest.getEmail()))")
    User userRequestToUser(String userId, UserRequest userRequest);

    @Mapping(target = "phone", expression = "java(CryptoUtil.encryptAES(userRequest.getPhone()))")
    @Mapping(target = "email", expression = "java(CryptoUtil.encryptAES(userRequest.getEmail()))")
    @Mapping(target = "phoneHash", expression = "java(CryptoUtil.generateHash(userRequest.getPhone()))")
    @Mapping(target = "emailHash", expression = "java(CryptoUtil.generateHash(userRequest.getEmail()))")
    User userRequestToUser(UserRequest userRequest);
}
