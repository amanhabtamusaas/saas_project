package com.organization_service.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobCategoryResponse {
    private UUID id;
    private String jobCategoryName;
    private String description;
    private UUID tenantId;
}
