package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private int code;
    private String error;
    private String message;
    private ZonedDateTime timestamp;

    public ErrorResponse(int code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.timestamp = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
