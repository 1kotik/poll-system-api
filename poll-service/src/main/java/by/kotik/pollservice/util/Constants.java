package by.kotik.pollservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    private static String JWT_SECRET;

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }

    public static String getJwtSecret(){
        return JWT_SECRET;
    }
}
