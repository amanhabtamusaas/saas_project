package com.auth_service.utility;

import com.auth_service.enums.AuthServiceResourceName;
import com.auth_service.enums.EmployeeServiceResourceName;
import com.auth_service.exception.ResourceNotFoundException;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResourceUtil {

    private final ResourceRepository resourceRepository;

    public void assignRoleToRelatedResources(UUID tenantId, String resourceName, Set<String> roles) {

        Map<String, List<String>> resourceMap = Map.of(
                EmployeeServiceResourceName.ADD_EMPLOYEE.getValue(), List.of(
                        AuthServiceResourceName.ADD_USER.getValue()
                )
        );

        List<String> relatedResourceNames = resourceMap.get(resourceName);
        if (relatedResourceNames != null) {
            relatedResourceNames.forEach(name -> {
                Resource resource = getResourceByName(tenantId, name);
                saveRolesToResource(resource, roles);
            });
        }
    }

    private Resource getResourceByName(UUID tenantId, String resourceName) {
        return resourceRepository.findByTenantIdAndResourceName(tenantId, resourceName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with name " + resourceName));
    }

    private void saveRolesToResource(Resource resource, Set<String> roles) {
        resource.setRequiredRoles(roles);
        resourceRepository.save(resource);
    }
}