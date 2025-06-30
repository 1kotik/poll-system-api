package by.kotik.authservice.validator;

import by.kotik.authservice.annotation.PasswordMatch;
import by.kotik.authservice.dto.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatch, UserRegistrationDto> {

    @Override
    public boolean isValid(UserRegistrationDto value, ConstraintValidatorContext context) {
        return value.getPassword() != null
                && value.getPassword().equals(value.getConfirmPassword());
    }
}
