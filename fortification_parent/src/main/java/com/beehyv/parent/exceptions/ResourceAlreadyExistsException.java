package com.beehyv.parent.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends CustomException {

    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
