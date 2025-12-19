package com.example.authservice.service;

import com.example.authservice.dto.request.CreateCredentialsRequest;
import com.example.authservice.event.UserRegisteredEvent;
import com.example.authservice.model.Credential;
import com.example.authservice.repository.CredentialRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InternalCredentialsService {

    private final PasswordEncoder passwordEncoder;
    private final CredentialRepository repository;

    public InternalCredentialsService(PasswordEncoder passwordEncoder, CredentialRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }


    public void createCredential(UserRegisteredEvent request) {
        Credential credential = new Credential(
                request.username(),
                passwordEncoder.encode(request.password())
        );
        repository.save(credential);
    }

    /*public void createCredential(CreateCredentialsRequest request) {
        boolean isPresent = repository.findByUsername(request.username()).isPresent();
        if (isPresent) {
            throw new IllegalStateException("Username is already in use");
        }
        Credential credential = new Credential(
                request.username(),
                passwordEncoder.encode(request.rawPassword())
        );
        repository.save(credential);
    }*/

    public void updateCredential(CreateCredentialsRequest request) {
        boolean isPresent = repository.findById(request.id()).isPresent();
        if (!isPresent) {
            throw new IllegalStateException("Wrong id or user details");
        }
        Credential credential = new Credential(
                request.id(),
                request.username(),
                passwordEncoder.encode(request.rawPassword())
        );
        repository.save(credential);
    }
}
