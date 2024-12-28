package com.recruitment_service.dto.clientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationLevelDto {
    private UUID id;
    private String educationLevelName;
    private String description;
    private UUID tenantId;
}