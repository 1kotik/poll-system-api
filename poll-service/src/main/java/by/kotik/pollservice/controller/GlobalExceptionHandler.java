package by.kotik.pollservice.controller;

import dto.ErrorResponse;
import exception.AppException;
import exception.GenericAuthenticationException;
import exception.GenericAuthorizationException;
import exception.GenericNotFoundException;
import exception.GenericValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GenericNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(GenericNotFoundException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(GenericValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(GenericValidationException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return getResponseEntity(new GenericValidationException("Illegal data format"));
    }

    @ExceptionHandler(GenericAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(GenericAuthenticationException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(GenericAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(GenericAuthorizationException e) {
        return getResponseEntity(e);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable e) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Internal Server Error", "Something went wrong"));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public void handleMissingHeaderException(MissingRequestHeaderException e){
        throw new GenericAuthenticationException(String.format("Missing header '%s'", e.getHeaderName()));
    }

    private ResponseEntity<ErrorResponse> getResponseEntity(AppException e) {
        return ResponseEntity.status(e.getCode())
                .body(new ErrorResponse(e.getCode(), e.getError(), e.getMessage()));
    }
}
