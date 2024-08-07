package com.insa.exception;

import jakarta.validation.constraints.NotBlank;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(
            @NotBlank(message = "Department ID cannot be blank") String message) {
        super(message);
    }
}
