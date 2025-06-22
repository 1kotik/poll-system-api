package by.kotik.authservice.service;

import by.kotik.authservice.client.UserServiceClient;
import by.kotik.authservice.dto.CustomUserDetails;
import dto.UserCredentialsDto;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentialsDto userAuthDto = userServiceClient.getUserByName(username)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return new CustomUserDetails(userAuthDto.getUsername(), userAuthDto.getPassword(),
                userAuthDto.getRoles().stream().map(SimpleGrantedAuthority::new).toList(), userAuthDto.getId());
    }

    public Optional<UserCredentialsDto> getUserByName(String username) {
        return userServiceClient.getUserByName(username);
    }

    public Optional<UserCredentialsDto> getUserByEmail(String email) {
        return userServiceClient.getUserByEmail(email);
    }

    public void save(UserCredentialsDto userAuthDto) {
        userServiceClient.createUser(userAuthDto);
    }
}
