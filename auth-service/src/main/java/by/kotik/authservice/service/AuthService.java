package by.kotik.authservice.service;

import by.kotik.authservice.dto.CustomUserDetails;
import by.kotik.authservice.dto.UserRegistrationDto;
import by.kotik.authservice.mapper.UserMapper;
import by.kotik.authservice.validator.PasswordValidator;
import dto.TokenDto;
import dto.UserAuthRequestDto;
import dto.UserDto;
import exception.ExistingCredentialsException;
import exception.GenericAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public TokenDto createToken(UserAuthRequestDto userAuthRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthRequestDto.getUsername(),
                    userAuthRequestDto.getPassword()));
        } catch (AuthenticationException e) {
            throw new GenericAuthenticationException("Invalid credentials");
        }
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userAuthRequestDto.getUsername());
        return new TokenDto(jwtService.generateToken(userDetails));
    }

    public UserDto createNewUser(UserRegistrationDto userRegistrationDto) {
        userDetailsService.getUserByName(userRegistrationDto.getUsername())
                .ifPresent(dto -> {
                    throw new ExistingCredentialsException("username");
                });
        userDetailsService.getUserByEmail(userRegistrationDto.getEmail())
                .ifPresent(dto -> {
                    throw new ExistingCredentialsException("email");
                });
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        return userDetailsService.save(userMapper.userRegistrationDtoToUserCredentialsDto(userRegistrationDto));
    }

}
