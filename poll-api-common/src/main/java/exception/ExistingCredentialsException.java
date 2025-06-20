package exception;

public class ExistingCredentialsException extends GenericValidationException {
    public ExistingCredentialsException(String credentialName) {
        super(String.format("This %s already exists.", credentialName));
    }
}
