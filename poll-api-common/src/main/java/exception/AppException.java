package exception;

public class AppException extends RuntimeException {
    private final int code;
    private final String error;

    public AppException(int code, String error, String message) {
        super(message);
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
