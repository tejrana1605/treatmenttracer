package com.example.authservice.clients;

import com.example.authservice.common.StandardResponse;
import com.example.authservice.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERPROFILE-SERVICE")
public interface UserProfileClient {

    @GetMapping("/api/users/{username}")
    StandardResponse<UserProfileResponse> getUserByUsername(
            @PathVariable String username
    );
}