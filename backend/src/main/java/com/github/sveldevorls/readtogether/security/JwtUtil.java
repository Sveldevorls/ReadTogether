package com.github.sveldevorls.readtogether.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(int id, String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                   .subject(Integer.toString(id))
                   .claim("username", username)
                   .claim("role", role)
                   .issuedAt(now)
                   .expiration(expiryDate)
                   .signWith(getSigningKey())
                   .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                   .verifyWith(getSigningKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    public int getIdFromToken(String token) {
        return Integer.parseInt(validateToken(token).getSubject());
    }

    public String getUsernameFromToken(String token) {
        return validateToken(token).get("username", String.class);
    }

    public String getRoleFromToken(String token) {
        return validateToken(token).get("role", String.class);
    }
}
