package com.example.authservice.dto.response;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {}
