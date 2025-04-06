package com.crud.example.Jwt.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET ="m3yux0D89pOh72HF8FYXpRf27/OK3OaXkEX0x9zNmGsm3yux0D89pOh72HF8FYXpRf27/OK3OaXkEX0x9zNmGsm3yux0D89pOh72HF8FYXpRf27/OK3OaXkEX0x9zNmGsm3yux0D89pOh72HF8FYXpRf27/OK3OaXkEX0x9zNmGs";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
