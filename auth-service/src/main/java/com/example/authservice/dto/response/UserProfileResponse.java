package com.example.authservice.dto.response;

public record UserProfileResponse(
        String username,
        String email,
        String status,
        String role
) {}
