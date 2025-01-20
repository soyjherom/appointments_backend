package com.jherom.ventures.appointments_backend.domain.mappers;

import com.jherom.ventures.appointments_backend.exceptions.DecryptionException;
import com.jherom.ventures.appointments_backend.models.User;
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
        imports = {CryptoUtil.class, DecryptionException.class})
public interface UserMapper {

    @Named("getDecryptedPhone")
    default String getDecryptedPhone(String phone) throws DecryptionException {
        return CryptoUtil.decrypt(phone);
    }

    @Named("getDecryptedEmail")
    default String getDecryptedEmail(String email) throws DecryptionException {
        return CryptoUtil.decrypt(email);
    }

    default Page<UserResponse> pageToUserResponsePage(Page<User> userPage) {
        return userPage.map(this::userToUserResponse);
    }

    @Mapping(target = "id", expression = "java(user.getId())")
    @Mapping(target = "name", expression = "java(user.getName())")
    @Mapping(target = "phone", qualifiedByName = "getDecryptedPhone")
    @Mapping(target = "email", qualifiedByName = "getDecryptedEmail")
    UserResponse userToUserResponse(User user);
}
