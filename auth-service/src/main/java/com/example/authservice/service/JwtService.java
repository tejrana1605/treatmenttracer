package com.example.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final String secret = "very-secret-key-for-training";

    @Value("${jwt.expiration}")
    private long expirationSeconds;

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    public String generateToken(String username, String role) {

        Instant now = Instant.now();

        return Jwts
                .builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationSeconds))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();

    }

}
