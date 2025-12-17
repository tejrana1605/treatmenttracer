package com.example.userprofile.controller;

import com.example.userprofile.common.StandardResponse;
import com.example.userprofile.dto.CreateUserProfileRequest;
import com.example.userprofile.dto.UserProfileResponse;
import com.example.userprofile.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public StandardResponse<UserProfileResponse> create(
            @RequestBody CreateUserProfileRequest request) {

        return StandardResponse.success(
                "User profile created",
                service.create(request)
        );
    }

    @PutMapping("/update")
    public StandardResponse<UserProfileResponse> update(@PathVariable Long id,
                              @RequestBody CreateUserProfileRequest request) {
        return StandardResponse.success(
                "User profile created",
                service.update(id, request)
        );
    }

    @GetMapping("/{username}")
    public StandardResponse<UserProfileResponse> getByUsername(
            @PathVariable String username) {

        return StandardResponse.success(
                "User profile fetched",
                service.getByUsername(username)
        );
    }
}
