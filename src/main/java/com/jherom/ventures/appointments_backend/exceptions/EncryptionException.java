package com.jherom.ventures.appointments_backend.exceptions;

import org.springframework.http.HttpStatus;

public class EncryptionException extends CommonException{
    public EncryptionException() {
        super("encryption_error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
