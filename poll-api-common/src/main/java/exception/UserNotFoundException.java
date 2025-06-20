package exception;

public class UserNotFoundException extends GenericNotFoundException {
    public UserNotFoundException(String username) {
        super("User not found: " + username);
    }
}
