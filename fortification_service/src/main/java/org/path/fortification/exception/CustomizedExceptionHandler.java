package org.path.fortification.exception;

import org.path.parent.exceptions.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.PersistenceException;

@ControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler({JsonProcessingException.class})
    public ResponseEntity<ExceptionResponse> handleExceptions(JsonProcessingException exception, WebRequest webRequest) {
        return ExceptionResponse.response(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PersistenceException.class})
    public ResponseEntity<ExceptionResponse> handleExceptions(PersistenceException exception, WebRequest webRequest) {
        return ExceptionResponse.response(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}