package com.jherom.ventures.appointments_backend.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CommonException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
    public UserNotFoundException() {
        super("user_not_found", HttpStatus.NOT_FOUND);
    }
}
