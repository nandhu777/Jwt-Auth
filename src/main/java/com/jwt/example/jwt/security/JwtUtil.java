package com.jwt.example.jwt.security;

import com.jwt.example.jwt.enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET ="m3yux0D89pOh72HF8FYXpRf27/OK3OaXkEX0x9zNmGsm3yux0D89pOh72HF8FYXpRf27/OK3OaXkEX0x9zNmGsm3yux0D89pOh72HF8FYXpRf27/OK3OaXkEX0x9zNmGsm3yux0D89pOh72HF8FYXpRf27/OK3OaXkEX0x9zNmGs";

    public String generateToken(String identifier, Role role) {
        return Jwts.builder()
                .setSubject(identifier)
                .claim("role", role.name()) // Adding role as a claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String getIdentifierFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Retrieve the Identifier from the token
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
