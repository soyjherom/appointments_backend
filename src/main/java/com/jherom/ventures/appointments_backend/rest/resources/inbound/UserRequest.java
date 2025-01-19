package com.jherom.ventures.appointments_backend.rest.resources.inbound;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class UserRequest {
    private String name;
    private String email;
    private String phone;
}
