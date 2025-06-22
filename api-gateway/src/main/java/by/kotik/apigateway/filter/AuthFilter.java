package by.kotik.apigateway.filter;


import by.kotik.apigateway.validator.RouteValidator;
import exception.GenericAuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import util.JwtUtils;

import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    @Autowired
    private RouteValidator routeValidator;
    @Value("${jwt.secret}")
    private String secret;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
                ServerHttpRequest request = exchange.getRequest();
                String token = getToken(request);
                List<String> roles = JwtUtils.getRoles(token, secret);
                routeValidator.isRouteAccessible(roles, request.getURI().getPath());
                return chain.filter(exchange);
        };
    }

    private String getToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new GenericAuthorizationException("No Authorization header found");
        } else {
            authHeader = JwtUtils.extractTokenFromHeader(authHeader);
        }
        return authHeader;
    }

    public static class Config {
    }
}
