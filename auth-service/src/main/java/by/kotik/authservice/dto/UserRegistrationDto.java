package by.kotik.authservice.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
