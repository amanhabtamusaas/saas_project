package com.insa.leave_service.exception;


import jakarta.validation.constraints.NotBlank;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(
            @NotBlank(message = "Tenant ID cannot be blank") String message) {
        super(message);
    }
}

