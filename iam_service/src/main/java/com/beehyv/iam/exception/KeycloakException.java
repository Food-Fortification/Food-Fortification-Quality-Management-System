package com.beehyv.iam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class KeycloakException extends RuntimeException{
    public KeycloakException(String message) {
        super(message);
    }
}
