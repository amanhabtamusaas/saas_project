package com.insa.service;

import com.insa.dto.TenantDto;
import com.insa.dto.request.RoleRequest;
import com.insa.dto.response.RoleResponse;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.RoleMapper;
import com.insa.utility.KeycloakSecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final KeycloakSecurityUtil keycloakSecurityUtil;
    private final RoleMapper roleMapper;
    private final TenantServiceClient tenantServiceClient;

    @Value("${keycloak.realm}")
    private String realm;

    public RoleResponse addRole(Long tenantId,
                                RoleRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        RoleRepresentation roleRepresentation = roleMapper.mapToEntity(request);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        RolesResource rolesResource = realmResource.roles();
        roleRepresentation.singleAttribute("tenantId", tenant.getId().toString());
        rolesResource.create(roleRepresentation);
        return roleMapper.mapToDto(roleRepresentation);
    }

    public List<RoleResponse> getAllRoles(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        RolesResource rolesResource = realmResource.roles();
        List<RoleRepresentation> roleRepresentations = rolesResource.list();
        return roleRepresentations.stream()
//                .filter(role -> {
//                    Optional<String> roleTenantId = Optional.ofNullable(role.getAttributes())
//                            .map(attr -> attr.get("tenantId"))
//                            .map(list -> list.get(0));
//                    return roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString());
//                })
                .map(roleMapper::mapToDto)
                .toList();
    }

    public RoleResponse getRoleByName(Long tenantId,
                                      String roleName) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        RolesResource rolesResource = realmResource.roles();
        RoleResource roleResource = rolesResource.get(roleName);
        RoleRepresentation roleRepresentation = roleResource.toRepresentation();
        Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString())) {
            return roleMapper.mapToDto(roleRepresentation);
        } else {
            throw new ResourceNotFoundException(
                    "Role not found with name '" + roleName + "' in the specified tenant");
        }
    }

    public RoleResponse updateRole(Long tenantId,
                                   String roleName,
                                   RoleRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        RolesResource rolesResource = realmResource.roles();
        RoleResource roleResource = rolesResource.get(roleName);
        RoleRepresentation roleRepresentation = roleResource.toRepresentation();
        roleRepresentation = roleMapper.updateEntity(roleRepresentation, request);
        Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString())) {
            roleResource.update(roleRepresentation);
            return roleMapper.mapToDto(roleRepresentation);
        } else {
            throw new ResourceNotFoundException(
                    "Role not found with name '" + roleName + "' in the specified tenant");
        }
    }

    public void deleteRole(Long tenantId,
                           String roleName) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        RolesResource rolesResource = realmResource.roles();
        RoleResource roleResource = rolesResource.get(roleName);
        RoleRepresentation roleRepresentation = roleResource.toRepresentation();
        Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString())) {
            rolesResource.deleteRole(roleRepresentation.getName());
        } else {
            throw new ResourceNotFoundException(
                    "Role not found with name '" + roleName + "' in the specified tenant");
        }
    }
}
