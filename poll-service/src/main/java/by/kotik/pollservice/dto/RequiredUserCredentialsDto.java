package by.kotik.pollservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RequiredUserCredentialsDto {
    private UUID id;
    private List<String> roles;
}
