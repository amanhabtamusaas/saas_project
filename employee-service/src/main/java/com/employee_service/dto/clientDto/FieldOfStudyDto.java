package com.employee_service.dto.clientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldOfStudyDto {
    private UUID id;
    private String fieldOfStudy;
    private String description;
    private UUID tenantId;

    // Getters and Setters
}
