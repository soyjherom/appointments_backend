package com.jherom.ventures.appointments_backend.domain.mappers;

import com.jherom.ventures.appointments_backend.exceptions.CommonException;
import com.jherom.ventures.appointments_backend.exceptions.DecryptionException;
import com.jherom.ventures.appointments_backend.exceptions.EncryptionException;
import com.jherom.ventures.appointments_backend.exceptions.HashingException;
import com.jherom.ventures.appointments_backend.models.User;
import com.jherom.ventures.appointments_backend.rest.resources.inbound.UserRequest;
import com.jherom.ventures.appointments_backend.rest.resources.outbound.UserResponse;
import com.jherom.ventures.appointments_backend.utils.CryptoUtil;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Mapper(componentModel="spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = CryptoUtil.class,
        imports = {UUID.class, CryptoUtil.class, EncryptionException.class, DecryptionException.class})
public interface UserMapper {

    @Named("getUserId")
    default String getUserId(String userId) { return userId; }

    @Named("getEncryptedPhone")
    default String getEncryptedPhone(String phone) throws EncryptionException {
        return CryptoUtil.encrypt(phone);
    }

    @Named("getEncryptedEmail")
    default String getEncryptedEmail(String email) throws EncryptionException {
        return CryptoUtil.encrypt(email);
    }

    @Named("getDecryptedPhone")
    default String getDecryptedPhone(String phone) throws DecryptionException {
        return CryptoUtil.decrypt(phone);
    }

    @Named("getDecryptedEmail")
    default String getDecryptedEmail(String email) throws DecryptionException {
        return CryptoUtil.decrypt(email);
    }

    @Named("getHashedPhone")
    default String getHashedPhone(String phone) throws HashingException {
        return CryptoUtil.getHash(phone);
    }

    @Named("getHashedEmail")
    default String getHashedEmail(String email) throws HashingException {
        return CryptoUtil.getHash(email);
    }

    default Page<UserResponse> pageToUserResponsePage(Page<User> userPage) {
        return userPage.map(this::userToUserResponse);
    }

    @Mapping(target = "id", expression = "java(user.getId())")
    @Mapping(target = "name", expression = "java(user.getName())")
    @Mapping(target = "phone", qualifiedByName = "getDecryptedPhone")
    @Mapping(target = "email", qualifiedByName = "getDecryptedEmail")
    UserResponse userToUserResponse(User user);

    @Mapping(target = "id", expression = "java(getUserId(userId))")
    @Mapping(target = "name", expression = "java(userRequest.getName())")
    @Mapping(target = "email", expression = "java(getEncryptedEmail(userRequest.getEmail()))")
    @Mapping(target = "phone", expression = "java(getEncryptedPhone(userRequest.getPhone()))")
    @Mapping(target = "phoneHash", expression = "java(getHashedPhone(userRequest.getPhone()))")
    @Mapping(target = "emailHash", expression = "java(getHashedEmail(userRequest.getEmail()))")
    User userRequestToUser(String userId, UserRequest userRequest) throws CommonException;

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "name", expression = "java(userRequest.getName())")
    @Mapping(target = "email", expression = "java(getEncryptedEmail(userRequest.getEmail()))")
    @Mapping(target = "phone", expression = "java(getEncryptedPhone(userRequest.getPhone()))")
    @Mapping(target = "phoneHash", expression = "java(getHashedPhone(userRequest.getPhone()))")
    @Mapping(target = "emailHash", expression = "java(getHashedEmail(userRequest.getEmail()))")
    User userRequestToUser(UserRequest userRequest) throws CommonException;
}
