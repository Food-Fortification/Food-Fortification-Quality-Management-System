package org.path.iam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MapperException extends RuntimeException {
    public MapperException(String message) {
        super(message);
    }
}
