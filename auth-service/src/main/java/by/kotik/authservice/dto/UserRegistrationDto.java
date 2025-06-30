package by.kotik.authservice.dto;

import by.kotik.authservice.annotation.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatch
public class UserRegistrationDto {
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotBlank
    @Size(min = 8)
    private String confirmPassword;
}
