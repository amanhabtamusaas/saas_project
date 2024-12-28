package com.auth_service.data;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.enums.AuthServiceResourceName;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class AuthServiceResource {

    private final ResourceRepository resourceRepository;
    private final ValidationUtil validationUtil;

    public void storeEndpoints(TenantDto tenant) {
        List<Resource> resources = new ArrayList<>();

        /* User */
        resources.add(mapToEntity(AuthServiceResourceName.ADD_USER.getValue(), "User",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_ALL_USERS.getValue(), "User",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_USER_ROLES.getValue(), "User",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_USER_BY_ID.getValue(), "User",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.UPDATE_USER.getValue(), "User",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.DELETE_USER.getValue(), "User",
                null, tenant.getId()));

        /* Role */
        resources.add(mapToEntity(AuthServiceResourceName.ADD_ROLE.getValue(), "Role",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.ASSIGN_ROLE_TO_USER.getValue(), "Role",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_ALL_ROLES.getValue(), "Role",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_ROLE_BY_NAME.getValue(), "Role",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.UPDATE_ROLE.getValue(), "Role",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.UNASSIGN_ROLE_FROM_USER.getValue(), "Role",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.DELETE_ROLE.getValue(), "Role",
                null, tenant.getId()));

        /* Resource */
        resources.add(mapToEntity(AuthServiceResourceName.ASSIGN_ROLE_TO_RESOURCE.getValue(), "Resource",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_ALL_RESOURCES.getValue(), "Resource",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_RESOURCES_BY_ROLE_NAME.getValue(), "Resource",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_RESOURCE_BY_ID.getValue(), "Resource",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_RESOURCE_BY_NAME.getValue(), "Resource",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.GET_RESOURCES_BY_GROUP.getValue(), "Resource",
                null, tenant.getId()));
        resources.add(mapToEntity(AuthServiceResourceName.UNASSIGN_ROLE_FROM_RESOURCE.getValue(), "Resource",
                null, tenant.getId()));

        for (Resource resource : resources) {
            if (resourceRepository.existsByTenantIdAndResourceName(tenant.getId(), resource.getResourceName())) {
                throw new ResourceExistsException(
                        "Resource already exists with name '" + resource.getResourceName() + "'");
            }
            resourceRepository.save(resource);
        }
    }

    private Resource mapToEntity(String requestName,
                                 String groupName,
                                 String roleName,
                                 UUID tenantId) {

        Resource resource = new Resource();
        resource.setResourceName(requestName);
        resource.setGroupName(groupName);
        resource.setTenantId(tenantId);
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        TenantDto tenant = validationUtil.getTenantById(tenantId);
        String adminRole = tenant.getAbbreviatedName().toLowerCase() + "_admin";
        roles.add(adminRole);
        if (roleName != null) {
            roles.add(roleName);
        }
        resource.setRequiredRoles(roles);

        return resource;
    }
}