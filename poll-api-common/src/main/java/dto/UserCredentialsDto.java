package dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserCredentialsDto {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
