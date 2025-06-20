package exception;

public class GenericAuthorizationException extends AppException {
    public GenericAuthorizationException(String message) {
        super(403, "Forbidden", message);
    }
}
