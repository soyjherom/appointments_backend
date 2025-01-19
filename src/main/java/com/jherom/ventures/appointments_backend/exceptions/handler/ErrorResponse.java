package com.jherom.ventures.appointments_backend.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ErrorResponse {
    private String message;
    private int statusCode;
    private long timestamp;
}
