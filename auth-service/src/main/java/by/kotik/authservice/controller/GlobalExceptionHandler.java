package by.kotik.authservice.controller;

import dto.ErrorResponse;
import exception.AppException;

import exception.GenericAuthenticationException;
import exception.GenericAuthorizationException;
import exception.GenericNotFoundException;
import exception.GenericValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GenericAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(GenericAuthenticationException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(GenericAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(GenericAuthorizationException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(GenericValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(GenericValidationException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(GenericNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(GenericNotFoundException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleOtherException(Throwable e) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Auth Service Error", "Something went wrong"));
    }

    private ResponseEntity<ErrorResponse> getResponseEntity(AppException e) {
        return ResponseEntity.status(e.getCode())
                .body(new ErrorResponse(e.getCode(), e.getError(), e.getMessage()));
    }
}
