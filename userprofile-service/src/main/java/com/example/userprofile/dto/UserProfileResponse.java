package com.example.userprofile.dto;

public record UserProfileResponse(
        String username,
        String email,
        String status,
        String role
) {}
