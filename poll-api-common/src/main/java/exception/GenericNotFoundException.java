package exception;

public class GenericNotFoundException extends AppException {
    public GenericNotFoundException(String message) {
        super(404, "Not Found", message);
    }
}
