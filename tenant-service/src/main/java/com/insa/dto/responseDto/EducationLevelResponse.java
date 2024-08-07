package com.insa.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationLevelResponse {
    private Long id;
    private String educationLevelName;
    private String description;
    private Long tenantId;

}