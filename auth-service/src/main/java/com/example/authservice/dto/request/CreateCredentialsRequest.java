package com.example.authservice.dto.request;

public record CreateCredentialsRequest(Long id, String username, String rawPassword) {
}
