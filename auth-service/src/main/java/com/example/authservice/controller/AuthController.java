package com.example.authservice.controller;

import com.example.authservice.common.StandardResponse;
import com.example.authservice.dto.response.AuthResponse;
import com.example.authservice.dto.request.LoginRequest;
import com.example.authservice.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public StandardResponse<AuthResponse> login(
            @RequestBody LoginRequest request) {

        return StandardResponse.success(
                "Authentication successful",
                service.authenticate(request)
        );
    }
}
