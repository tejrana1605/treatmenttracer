package com.example.userprofile.service;

import com.example.userprofile.clients.AuthInternalClient;
import com.example.userprofile.dto.CreateCredentialRequest;
import com.example.userprofile.dto.CreateUserProfileRequest;
import com.example.userprofile.dto.UserProfileResponse;
import com.example.userprofile.exceptions.ResourceNotFoundException;
import com.example.userprofile.model.UserProfile;
import com.example.userprofile.model.UserRole;
import com.example.userprofile.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository repository;
    private final AuthInternalClient authClient;

    public UserProfileService(UserProfileRepository repository, AuthInternalClient authClient) {
        this.repository = repository;
        this.authClient = authClient;
    }

    public UserProfileResponse create(CreateUserProfileRequest request) {

        UserProfile profile = new UserProfile(
                request.username(),
                request.email(),
                UserRole.valueOf(request.role())
        );

        repository.save(profile);

        // create credentials
        try {
            authClient.createCredentials(new CreateCredentialRequest(
                   null, request.username(), request.password()
            ));
        }catch (Exception exception) {
            // trigger a rollback
            throw new RuntimeException(exception);
        }

        return map(profile);
    }

    public UserProfileResponse update(Long id, CreateUserProfileRequest request) {

        UserProfile profile = new UserProfile(
                request.username(),
                request.email(),
                UserRole.valueOf(request.role())
        );
        profile.setId(id);

        repository.save(profile);

        // create credentials
        try {
            authClient.updateCredentials(new CreateCredentialRequest(id,
                    request.username(), request.password()
            ));
        }catch (Exception exception) {
            // trigger a rollback
            throw new RuntimeException(exception);
        }

        return map(profile);
    }

    public UserProfileResponse getByUsername(String username) {

        UserProfile profile = repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return map(profile);
    }

    /**
     * Internal-use endpoint (for Auth Service later)
     */
    public UserProfile getInternalByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private UserProfileResponse map(UserProfile profile) {
        return new UserProfileResponse(
                profile.getUsername(),
                profile.getEmail(),
                profile.getStatus().name(),
                profile.getRole().name()
        );
    }
}
