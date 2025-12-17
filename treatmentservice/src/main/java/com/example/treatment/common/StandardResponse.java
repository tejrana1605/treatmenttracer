package com.example.treatment.common;


import java.time.Instant;

public record StandardResponse<T>(
        boolean success,
        String message,
        T data,
        ErrorResponse error,
        Instant timestamp
) {

    public static <T> StandardResponse<T> success(String message, T data) {
        return new StandardResponse<>(true, message, data, null, Instant.now());
    }

    public static <T> StandardResponse<T> failure(String message, ErrorResponse error) {
        return new StandardResponse<>(false, message, null, error, Instant.now());
    }
}
