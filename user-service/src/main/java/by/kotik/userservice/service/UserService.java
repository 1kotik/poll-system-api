package by.kotik.userservice.service;

import by.kotik.userservice.entity.User;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.repository.UserRepository;
import dto.UserCredentialsDto;
import dto.UserDto;
import exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Transactional
    public UserDto save(UserCredentialsDto userDto) {
        User user = userMapper.userCredentialsDtoToUser(userDto);
        user.setRoles(new HashSet<>(List.of(roleService.getRoleByName("USER"))));
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Optional<UserCredentialsDto> getUserCredentialsByNameForAuthService(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userToUserCredentialsDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserCredentialsDto> getUserCredentialsByEmailForAuthService(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserCredentialsDto);
    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new UserNotFoundException("username"));
    }

    @Transactional(readOnly = true)
    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new UserNotFoundException("email"));
    }
}
