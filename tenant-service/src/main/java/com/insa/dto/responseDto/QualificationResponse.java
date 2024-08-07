package com.insa.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualificationResponse {
    private Long id;
    private String qualification;
    private String description;
    private Long tenantId;
   // private Long jobRegistrationId;

    // Getters and Setters
}
