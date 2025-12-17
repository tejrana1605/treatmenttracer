package com.example.userprofile.clients;

import com.example.userprofile.dto.CreateCredentialRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthInternalClient {

    @PostMapping("/internal/credentials")
    void createCredentials(@RequestBody CreateCredentialRequest request);

    @PostMapping("/internal/credentials/update")
    void updateCredentials(@RequestBody CreateCredentialRequest request);
}
