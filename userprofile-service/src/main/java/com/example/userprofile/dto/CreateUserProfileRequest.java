package com.example.userprofile.dto;

public record CreateUserProfileRequest(
        String username,
        String email,
        String role,
        String password
) {}
