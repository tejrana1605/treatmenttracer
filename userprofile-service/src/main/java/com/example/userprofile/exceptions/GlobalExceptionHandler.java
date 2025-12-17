package com.example.userprofile.exceptions;

import com.example.userprofile.common.ErrorResponse;
import com.example.userprofile.common.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardResponse<Void>> handleNotFound(ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        StandardResponse.failure(
                                "Request failed",
                                ErrorResponse.of("USER_NOT_FOUND", ex.getMessage())
                        )
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardResponse<Void>> handleRuntimeException(RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        StandardResponse.failure(
                                "Request failed",
                                ErrorResponse.of("USER_CREATION_FAILED", ex.getMessage())
                        )
                );
    }
}

