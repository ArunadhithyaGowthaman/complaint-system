package com.complaint.system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔐 Secret Key (keep constant)
    private final Key SECRET_KEY = 
            Keys.hmacShaKeyFor(
                "mysecretkeymysecretkeymysecretkey123456"
                        .getBytes()
            );

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    // 🔥 Generate Token
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔥 Extract Username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 🔥 Extract Role
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // 🔥 Validate Token
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }

    // 🔥 Check Expiration
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // 🔥 Extract Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
