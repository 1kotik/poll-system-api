package by.kotik.authservice.annotation;

import by.kotik.authservice.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordMatch {
    String message() default "Passwords do not match.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
