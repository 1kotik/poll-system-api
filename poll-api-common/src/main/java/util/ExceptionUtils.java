package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ErrorResponse;

public class ExceptionUtils {
    private ExceptionUtils() {}
    public static ErrorResponse mapToErrorResponse(String content, ObjectMapper objectMapper) {
        ErrorResponse errorResponse;
        try {
            errorResponse = objectMapper.readValue(content, ErrorResponse.class);
        } catch (Exception e) {
            errorResponse = new ErrorResponse(500, "Service Error", e.getMessage());
        }
        return errorResponse;
    }
}
