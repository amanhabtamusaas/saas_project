package com.hr_planning_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkUnitDto {
    private UUID id;
    private String workUnitName;
    private String description;
    private UUID tenantId;
}
