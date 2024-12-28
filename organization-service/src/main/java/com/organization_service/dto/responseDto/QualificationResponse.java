package com.organization_service.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualificationResponse {
    private UUID id;
    private String qualification;
    private String description;
    private UUID tenantId;
   // private UUID jobRegistrationId;

    // Getters and Setters
}
