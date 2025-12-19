package com.example.userprofile.event;

import java.time.Instant;

public record UserRegisteredEvent(String username, String password) {
}
