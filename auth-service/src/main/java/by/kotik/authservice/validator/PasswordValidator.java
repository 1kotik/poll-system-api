package by.kotik.authservice.validator;

import exception.GenericValidationException;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    public static final int MIN_LENGTH = 8;

    public boolean validatePassword(String password) {
        return password.length() >= MIN_LENGTH;
    }

    public void validatePasswordConfirmation(String password, String confirmationPassword) {
        if(!validatePassword(password) || !validatePassword(confirmationPassword)) {
            throw new GenericValidationException("Invalid password format");
        }
        if(!confirmationPassword.equals(password)) {
            throw new GenericValidationException("Passwords do not match");
        }
    }
}
