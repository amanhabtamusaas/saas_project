package com.organization_service.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    private String locationName;

    private UUID locationTypeId;
    private UUID tenantId;
}
