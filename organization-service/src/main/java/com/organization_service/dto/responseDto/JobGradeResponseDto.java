package com.organization_service.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobGradeResponseDto {

    private UUID id;

    private String jobGradeName;

    private String description;

    private UUID tenantId;



    // Getters and Setters


}

