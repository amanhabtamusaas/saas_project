package com.organization_service.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
        private UUID id;
        private String locationName;
        private UUID parentLocationId;
        private List<UUID> subLocationIds;
        private UUID locationTypeId;
        private UUID tenantId;
}
