package com.jherom.ventures.appointments_backend.exceptions;

import org.springframework.http.HttpStatus;

public class HashingException extends CommonException{
    public HashingException() {
        super("hashing_error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
