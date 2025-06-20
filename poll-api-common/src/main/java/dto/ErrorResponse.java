package dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int code;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
