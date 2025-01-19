package com.jherom.ventures.appointments_backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends Exception{
    private final HttpStatus status;
    public CommonException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
