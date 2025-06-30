package by.kotik.userservice.mapper;

import by.kotik.userservice.entity.Role;
import by.kotik.userservice.entity.User;
import dto.UserCredentialsDto;
import dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    User userCredentialsDtoToUser(UserCredentialsDto userDto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "extractRoleNames")
    UserCredentialsDto userToUserCredentialsDto(User user);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "extractRoleNames")
    UserDto userToUserDto(User user);

    @Named("extractRoleNames")
    default Set<String> extractRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

}
