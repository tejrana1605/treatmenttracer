package com.example.authservice.dto.request;

public record LoginRequest(
        String username,
        String password
) {}
