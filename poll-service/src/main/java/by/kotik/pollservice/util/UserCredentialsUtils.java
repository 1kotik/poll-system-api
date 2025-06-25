package by.kotik.pollservice.util;

import by.kotik.pollservice.dto.RequiredUserCredentialsDto;
import exception.GenericAuthorizationException;
import util.JwtUtils;

import java.util.UUID;

public class UserCredentialsUtils {
    private UserCredentialsUtils() {
    }

    public static RequiredUserCredentialsDto getUserIdFromAuthHeader(String authHeader) {
        String token = JwtUtils.extractTokenFromHeader(authHeader);
        return new RequiredUserCredentialsDto(JwtUtils.getId(token, Constants.getJwtSecret()),
                JwtUtils.getRoles(token, Constants.getJwtSecret()));
    }

    public static void validateUserIds(UUID pollCreatorId, RequiredUserCredentialsDto userDto) {
        if (!(pollCreatorId.equals(userDto.getId()) || userDto.getRoles().contains("ADMIN"))) {
            throw new GenericAuthorizationException("Unauthorized access");
        }
    }
}
