package com.example.userprofile.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String productNotFound) {
        super(productNotFound);
    }
}
