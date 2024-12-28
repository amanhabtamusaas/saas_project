package com.training_service.dto.clientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto {

    private UUID id;
    private String resourceName;
    private String groupName;
    private Set<String> requiredRoles;
    private String description;
    private UUID tenantId;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
