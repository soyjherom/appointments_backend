package com.jherom.ventures.appointments_backend.exceptions.handler;

import com.jherom.ventures.appointments_backend.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException ex) {
        return getErrorResponse(ex.getMessage(), ex.getStatus());
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .statusCode(status.value())
                        .message(message)
                        .timestamp(System.currentTimeMillis())
                        .build());
    }
}
