package util;

import exception.GenericAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtUtils {
    private JwtUtils(){

    }

    public static void validateToken(String token, String secret) {
        parseJws(token, secret);
    }

    public static List<String> getRoles(String token, String secret) {
        return parseJws(token, secret).getPayload()
                .get("roles", List.class);
    }

    public static Jws<Claims> parseJws(String token, String secret) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey(secret))
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            throw new GenericAuthenticationException("Invalid JWT");
        }
    }

    public static SecretKey getKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
