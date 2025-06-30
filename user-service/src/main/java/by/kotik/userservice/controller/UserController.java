package by.kotik.userservice.controller;

import by.kotik.userservice.service.UserService;
import dto.UserCredentialsDto;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/auth-name/{username}")
    public Optional<UserCredentialsDto> getUserCredentialsByNameForAuthService(@PathVariable String username) {
        return userService.getUserCredentialsByNameForAuthService(username);
    }

    @GetMapping("/auth-email/{email}")
    public Optional<UserCredentialsDto> getUserCredentialsByEmailForAuthService(@PathVariable String email) {
        return userService.getUserCredentialsByEmailForAuthService(email);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UserCredentialsDto userDto) {
        userService.save(userDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }
}
