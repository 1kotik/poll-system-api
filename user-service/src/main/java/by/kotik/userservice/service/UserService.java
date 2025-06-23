package by.kotik.userservice.service;

import by.kotik.userservice.entity.User;
import by.kotik.userservice.mapper.UserMapper;
import by.kotik.userservice.repository.UserRepository;
import dto.UserCredentialsDto;
import dto.UserDto;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void save(UserCredentialsDto userDto) {
        User user = userMapper.userCredentialsDtoToUser(userDto);
        user.setRoles(new HashSet<>(List.of(roleService.getRoleByName("USER"))));
        userRepository.save(user);
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
