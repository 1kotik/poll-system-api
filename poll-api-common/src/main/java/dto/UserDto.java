package dto;

import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private Instant createdAt;
    private boolean isActive;
    private Set<String> roles;
}
