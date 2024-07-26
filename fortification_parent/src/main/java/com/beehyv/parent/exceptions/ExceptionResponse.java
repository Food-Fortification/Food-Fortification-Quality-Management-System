package com.beehyv.parent.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ExceptionResponse {
    private String message;

    public static ExceptionResponse from(String message) {
        return new ExceptionResponse(message);
    }

    public static ResponseEntity<ExceptionResponse> response(String message, HttpStatus status) {
        log.error("Error occurred of type: {} and Message: {}", status.name(), message);
        return new ResponseEntity<>(from(message), status);
    }

    public static ResponseEntity<Object> responseObject(String message, HttpStatus status) {
        log.error("Error occurred of type: {} and Message: {}", status.name(), message);
        return new ResponseEntity<>(from(message), status);
    }
}
