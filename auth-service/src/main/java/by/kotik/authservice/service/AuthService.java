package by.kotik.authservice.service;

import by.kotik.authservice.dto.CustomUserDetails;
import by.kotik.authservice.dto.UserRegistrationDto;
import by.kotik.authservice.mapper.UserMapper;
import by.kotik.authservice.validator.PasswordValidator;
import dto.TokenDto;
import dto.UserAuthRequestDto;
import exception.ExistingCredentialsException;
import exception.GenericAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public void createNewUser(UserRegistrationDto userRegistrationDto) {
        passwordValidator.validatePasswordConfirmation(userRegistrationDto.getPassword(),
                userRegistrationDto.getConfirmPassword());
        userDetailsService.getUserByName(userRegistrationDto.getUsername())
                .ifPresent(dto -> {
                    throw new ExistingCredentialsException("username");
                });
        userDetailsService.getUserByEmail(userRegistrationDto.getEmail())
                .ifPresent(dto -> {
                    throw new ExistingCredentialsException("email");
                });
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userDetailsService.save(userMapper.userRegistrationDtoToUserCredentialsDto(userRegistrationDto));
    }

}
