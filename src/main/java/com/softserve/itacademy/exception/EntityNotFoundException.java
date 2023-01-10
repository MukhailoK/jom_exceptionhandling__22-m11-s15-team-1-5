package com.softserve.itacademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {

    }
    public EntityNotFoundException(String message) {
        super(message);
    }
}
