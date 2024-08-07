package com.insa.dto.apiDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationLevelDto {
    private Long id;
    private String educationLevelName;
    private String description;
    private Long tenantId;
}