package com.insa.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobGradeResponseDto {

    private Long id;

    private String jobGradeName;

    private String description;

    private Long tenantId;



    // Getters and Setters


}

