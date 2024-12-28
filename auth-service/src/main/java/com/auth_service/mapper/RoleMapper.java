package com.auth_service.mapper;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.dto.request.RoleRequest;
import com.auth_service.dto.response.RoleResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleMapper {

    public RoleRepresentation mapToEntity(TenantDto tenant,
                                          RoleRequest request) {

        String abbreviatedName = tenant.getAbbreviatedName().toLowerCase();
        String roleName = abbreviatedName + "_"+ request.getRoleName().toLowerCase();

        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(request.getDescription());
        roleRepresentation.singleAttribute("tenantId", tenant.getId().toString());

        return roleRepresentation;
    }

    public RoleResponse mapToDto(RoleRepresentation roleRepresentation) {

        RoleResponse response = new RoleResponse();
        response.setId(roleRepresentation.getId());
        response.setRoleName(roleRepresentation.getName());
        response.setDescription(roleRepresentation.getDescription());
        if (roleRepresentation.getAttributes().get("tenantId").get(0) != null ||
                !roleRepresentation.getAttributes().isEmpty()) {
            response.setTenantId(roleRepresentation.getAttributes().get("tenantId").get(0));
        }

        return response;
    }


    public RoleRepresentation updateEntity(TenantDto tenant,
                                           RoleRepresentation roleRepresentation,
                                           RoleRequest request) {

        String abbreviatedName = tenant.getAbbreviatedName().toLowerCase();

        if (request.getRoleName() != null) {
            String roleName = abbreviatedName + "_"+ request.getRoleName().toLowerCase();
            roleRepresentation.setName(roleName);
        }
        if (request.getDescription() != null) {
            roleRepresentation.setDescription(request.getDescription());
        }

        return roleRepresentation;
    }
}
