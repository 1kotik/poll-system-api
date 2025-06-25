package by.kotik.authservice.controller;

import by.kotik.authservice.dto.UserRegistrationDto;
import by.kotik.authservice.service.AuthService;
import dto.TokenDto;
import dto.UserAuthRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenDto> createToken(@RequestBody UserAuthRequestDto userCredentials) {
        TokenDto token = authService.createToken(userCredentials);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        authService.createNewUser(userRegistrationDto);
        return ResponseEntity.ok().build();
    }
}
