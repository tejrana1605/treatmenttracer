package com.example.authservice.exceptions;

import com.example.authservice.common.ErrorResponse;
import com.example.authservice.common.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardResponse<Void>> handleAuth(AuthenticationException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        StandardResponse.failure(
                                "Authentication failed",
                                ErrorResponse.of("AUTH_FAILED", ex.getMessage())
                        )
                );
    }
}
