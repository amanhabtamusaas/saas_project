package com.hr_planning_service.exception;

import jakarta.validation.constraints.NotBlank;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException( String message) {
        super(message);
    }
}

