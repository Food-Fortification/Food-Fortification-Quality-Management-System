package com.beehyv.parent.exceptions;

import org.springframework.http.HttpStatus;

public class MapperException extends CustomException {
    public MapperException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
