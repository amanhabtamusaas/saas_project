package com.insa.mapper;

import com.insa.dto.request.RoleRequest;
import com.insa.dto.response.RoleResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleRepresentation mapToEntity(RoleRequest request) {

        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(request.getRoleName());
        roleRepresentation.setDescription(request.getDescription());

        return roleRepresentation;
    }

    public RoleResponse mapToDto(RoleRepresentation roleRepresentation) {

        RoleResponse response = new RoleResponse();
        response.setId(roleRepresentation.getId());
        response.setTenantId(roleRepresentation.getAttributes().get("tenantId").get(0));
        response.setRoleName(roleRepresentation.getName());
        response.setDescription(roleRepresentation.getDescription());

        return response;
    }

    public RoleRepresentation updateEntity(RoleRepresentation roleRepresentation,
                                           RoleRequest request) {

        if (request.getRoleName() != null) {
            roleRepresentation.setName(request.getRoleName());
        }
        if (request.getDescription() != null) {
            roleRepresentation.setDescription(request.getDescription());
        }

        return roleRepresentation;
    }
}
