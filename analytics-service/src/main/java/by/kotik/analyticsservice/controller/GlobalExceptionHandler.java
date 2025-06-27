package by.kotik.analyticsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ErrorResponse;
import exception.AppException;
import exception.GenericNotFoundException;
import exception.GenericValidationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import util.ExceptionUtils;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException e) {
        return ResponseEntity.status(e.getCode())
                .body(new ErrorResponse(e.getCode(), e.getError(), e.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
        ErrorResponse errorResponse = ExceptionUtils.mapToErrorResponse(e.contentUTF8(), objectMapper);
        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        return handleAppException(new GenericNotFoundException("Resource not found: " + e.getResourcePath()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return handleAppException(new GenericValidationException("Illegal data format"));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Throwable e) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Analytics Service Error", e.getMessage()));
    }

}
