package by.kotik.pollservice.controller;

import dto.ErrorResponse;
import exception.AppException;
import exception.GenericNotFoundException;
import exception.GenericValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable e) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Internal Server Error", "Something went wrong"));
    }

    private ResponseEntity<ErrorResponse> getResponseEntity(AppException e) {
        return ResponseEntity.status(e.getCode())
                .body(new ErrorResponse(e.getCode(), e.getError(), e.getMessage()));
    }
}
