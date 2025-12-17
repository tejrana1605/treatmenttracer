package com.example.authservice.service;

import com.example.authservice.clients.UserProfileClient;
import com.example.authservice.dto.response.AuthResponse;
import com.example.authservice.dto.request.LoginRequest;
import com.example.authservice.exceptions.AuthenticationException;
import com.example.authservice.model.Credential;
import com.example.authservice.repository.CredentialRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    private final CredentialRepository credentialRepository;
    private final UserProfileClient userProfileClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            CredentialRepository credentialRepository,
            UserProfileClient userProfileClient,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.credentialRepository = credentialRepository;
        this.userProfileClient = userProfileClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse authenticate(LoginRequest request) {

        Credential credential = credentialRepository.findByUsername(request.username())
                .orElseThrow(() -> new AuthenticationException("Invalid credentials") {
                });

        if (!passwordEncoder.matches(request.password(), credential.getPasswordHash())) {
            throw new AuthenticationException("Invalid credentials");
        }

        var userResponse = userProfileClient.getUserByUsername(request.username());

        if (!"ACTIVE".equals(userResponse.data().status())) {
            throw new AuthenticationException("User is not active");
        }

        String token = jwtService.generateToken(
                userResponse.data().username(),
                userResponse.data().role()
        );

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds()
        );
    }
}
