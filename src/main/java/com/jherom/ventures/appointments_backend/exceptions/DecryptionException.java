package com.jherom.ventures.appointments_backend.exceptions;

import org.springframework.http.HttpStatus;

public class DecryptionException extends CommonException{
    public DecryptionException() {
        super("decryption_error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
