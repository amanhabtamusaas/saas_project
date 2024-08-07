package com.insa.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationTypeRequest {
    @NotBlank(message = "Location type name cannot be null or empty")
    private String locationTypeName;

    @NotBlank(message = "Description cannot be null or empty")
    private String description;
    private Long tenantId;
}
