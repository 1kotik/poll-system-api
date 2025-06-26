package by.kotik.analyticsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ErrorResponse;
import exception.AppException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return mapToErrorResponse(e.contentUTF8());
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Throwable e) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(500, "Analytics Service Error", e.getMessage()));
    }

    private ResponseEntity<ErrorResponse> mapToErrorResponse(String content) {
        ErrorResponse errorResponse;
        try {
            errorResponse = objectMapper.readValue(content, ErrorResponse.class);
        } catch (Exception e) {
            errorResponse = new ErrorResponse(500, "Service Error", e.getMessage());
        }
        return ResponseEntity.status(errorResponse.getCode()).body(errorResponse);
    }
}
