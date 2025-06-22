package by.kotik.apigateway.validator;

import exception.GenericAuthorizationException;
import exception.GenericNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {
    List<String> openEndpoints;
    List<String> userEndpoints;
    List<String> adminEndpoints;
    List<String> forbiddenEndpoints;

    public RouteValidator() {
        openEndpoints = getOpenEndpoints();
        userEndpoints = getUserEndpoints();
        adminEndpoints = getAdminEndpoints();
        forbiddenEndpoints = getForbiddenEndpoints();
    }

    private List<String> getOpenEndpoints() {
        return List.of("/auth/register", "/auth/token");
    }

    private List<String> getUserEndpoints() {
        return List.of("/polls");
    }

    private List<String> getAdminEndpoints() {
        return List.of();
    }

    private List<String> getForbiddenEndpoints() {
        return List.of("/users/get-by-username-auth", "/users/get-by-email-auth", "/users/create");
    }


    public void isRouteAccessible(List<String> roles, String path) {
        if (isPathMatchesAnyPattern(path, openEndpoints)) {
            return;
        }
        if (isPathMatchesAnyPattern(path, forbiddenEndpoints)) {
            throw new GenericAuthorizationException("Access Denied: Forbidden endpoint");
        }
        if (isPathMatchesAnyPattern(path, adminEndpoints)) {
            if (!roles.contains("ADMIN")) {
                throw new GenericAuthorizationException("Access Denied: Admin role required");
            }
            return;
        }
        if (isPathMatchesAnyPattern(path, userEndpoints)) {
            if (!roles.contains("USER")) {
                throw new GenericAuthorizationException("Access Denied: User role required");
            }
            return;
        }
        throw new GenericNotFoundException("Invalid URL");
    }

    private boolean isPathMatchesAnyPattern(String path, List<String> patterns) {
        return patterns.stream().anyMatch(path::startsWith);
    }
}
