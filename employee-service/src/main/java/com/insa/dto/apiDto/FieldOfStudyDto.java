package com.insa.dto.apiDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldOfStudyDto {
    private Long id;
    private String fieldOfStudy;
    private String description;
    private Long tenantId;

    // Getters and Setters
}
