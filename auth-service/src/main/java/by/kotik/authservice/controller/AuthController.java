package by.kotik.authservice.controller;

import by.kotik.authservice.dto.UserRegistrationDto;
import by.kotik.authservice.service.AuthService;
import dto.TokenDto;
import dto.UserAuthRequestDto;
import dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<TokenDto> createToken(@RequestBody UserAuthRequestDto userCredentials) {
        TokenDto token = authService.createToken(userCredentials);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        UserDto userDto = authService.createNewUser(userRegistrationDto);
        return ResponseEntity.ok(userDto);
    }
}
