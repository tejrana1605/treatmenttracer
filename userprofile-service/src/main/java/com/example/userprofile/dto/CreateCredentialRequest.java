package com.example.userprofile.dto;

public record CreateCredentialRequest(Long id, String username, String rawPassword) {
}
