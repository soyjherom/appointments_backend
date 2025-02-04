package com.jherom.ventures.appointments_backend.rest.resources.outbound;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String phone;
    private String email;
}
