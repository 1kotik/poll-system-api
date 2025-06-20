package exception;

public class GenericValidationException extends AppException {
    public GenericValidationException(String message) {
        super(400, "Bad Request", message);
    }
}
