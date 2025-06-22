package by.kotik.authservice.client;

import dto.UserCredentialsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/users/get-by-username-auth/{username}")
    Optional<UserCredentialsDto> getUserByName(@PathVariable String username);

    @GetMapping("/users/get-by-email-auth/{email}")
    Optional<UserCredentialsDto> getUserByEmail(@PathVariable String email);

    @PostMapping("/users/create")
    ResponseEntity<Void> createUser(@RequestBody UserCredentialsDto userDto);
}
