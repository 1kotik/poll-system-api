package by.kotik.authservice.mapper;

import by.kotik.authservice.dto.UserRegistrationDto;
import dto.UserCredentialsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCredentialsDto userRegistrationDtoToUserCredentialsDto(UserRegistrationDto userRegistrationDto);
}
