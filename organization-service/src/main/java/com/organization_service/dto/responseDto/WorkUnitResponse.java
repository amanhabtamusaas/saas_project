package com.organization_service.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class WorkUnitResponse {
    private UUID id;
    private String workUnitName;
    private String description;
    private UUID tenantId;

}
