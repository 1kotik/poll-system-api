package dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserCredentialsDto {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}
