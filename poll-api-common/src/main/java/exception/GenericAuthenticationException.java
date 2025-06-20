package exception;

public class GenericAuthenticationException extends AppException {
    public GenericAuthenticationException(String message) {
        super(401, "Unauthorized", message);
    }
}
