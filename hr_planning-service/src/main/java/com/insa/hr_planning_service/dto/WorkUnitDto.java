package com.insa.hr_planning_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkUnitDto {
    private Long id;
    private String workUnitName;
    private String description;
    private Long tenantId;
}
