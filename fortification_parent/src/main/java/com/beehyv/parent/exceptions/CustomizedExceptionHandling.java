package com.beehyv.parent.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ClientErrorException;
import javax.validation.ValidationException;
import javax.ws.rs.InternalServerErrorException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(NullPointerException exception, WebRequest webRequest) {
        return ExceptionResponse.response(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(SQLIntegrityConstraintViolationException exception, WebRequest webRequest) {
        return ExceptionResponse.response(exception.getMessage(), HttpStatus.valueOf(exception.getErrorCode()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(CustomException exception, WebRequest webRequest) {
        return ExceptionResponse.response(exception.getMessage(), exception.getStatus());
    }

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(ClientErrorException exception, WebRequest webRequest) {
        return ExceptionResponse.response(exception.getMessage(), HttpStatus.valueOf(exception.getResponse().getStatus()));
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(NoResultException exception, WebRequest webRequest) {
        logger.error("Message for NoResultException:" + exception.getMessage());
        // exception.printStackTrace();
        return ExceptionResponse.response("Oops! Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(InternalServerErrorException exception, WebRequest webRequest) {
        logger.error("Message for InternalServerErrorException: "+exception.getMessage());
        // exception.printStackTrace();
        return ExceptionResponse.response("Oops! Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(ValidationException exception, WebRequest webRequest) {
        if(exception instanceof ConstraintViolationException exp) {
            return ExceptionResponse.response(exp.toString(), HttpStatus.BAD_REQUEST);
        }
        return ExceptionResponse.response(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(javax.xml.bind.ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(javax.xml.bind.ValidationException exception, WebRequest webRequest) {
        return ExceptionResponse.response(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(exc.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        return new ResponseEntity<>(ExceptionResponse.from(ex.getMessage()), headers, status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleExceptions(RuntimeException exception, WebRequest webRequest) {

        if(exception instanceof CustomException){
            return ResponseEntity.status(((CustomException) exception).getStatus())
                    .body(exception.getMessage());
        }
        logger.info("Runtime exception occurred: " + exception.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body("Oops! Something went wrong");
    }
}