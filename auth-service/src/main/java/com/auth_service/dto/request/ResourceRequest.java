package com.auth_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceRequest {

    private String resourceName;
    private String groupName;
    private String description;
    private Set<String> requiredRoles;
}
