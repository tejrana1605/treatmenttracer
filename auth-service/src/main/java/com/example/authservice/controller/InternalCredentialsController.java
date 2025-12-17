package com.example.authservice.controller;

import com.example.authservice.dto.request.CreateCredentialsRequest;
import com.example.authservice.service.InternalCredentialsService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden // Hide it from swagger
@RestController
@RequestMapping("/internal/credentials")
public class InternalCredentialsController {

    private final InternalCredentialsService service;

    public InternalCredentialsController(InternalCredentialsService service) {
        this.service = service;
    }

    @PostMapping
    public void createCredentials(@RequestBody CreateCredentialsRequest request) {
        service.createCredential(request);
    }

    @PostMapping("/update")
    public void updateCredentials(@RequestBody CreateCredentialsRequest request) {
        service.updateCredential(request);
    }
}
