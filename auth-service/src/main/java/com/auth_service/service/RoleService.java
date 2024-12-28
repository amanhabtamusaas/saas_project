package com.auth_service.service;

import com.auth_service.config.RoleConverter;
import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.dto.request.RoleRequest;
import com.auth_service.dto.response.RoleResponse;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.exception.ResourceNotFoundException;
import com.auth_service.mapper.RoleMapper;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final Keycloak keycloak;
    private final RoleMapper roleMapper;
    private final UserService userService;
    private  final ValidationUtil validationUtil;
    private final RoleConverter roleConverter;

    @Value("${keycloak.realm}")
    private String realm;

    @Transactional
    public RoleResponse addRole(UUID tenantId,
                                RoleRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation roleRepresentation = roleMapper.mapToEntity(tenant, request );
        List<RoleRepresentation> roleRepresentations = rolesResource.list();
        for (RoleRepresentation existingRole : roleRepresentations) {
            if (existingRole.getName().equals(request.getRoleName())) {
                throw new ResourceExistsException(
                        "Role with name '" + request.getRoleName() + "' already exists");
            }
        }
        rolesResource.create(roleRepresentation);
        roleRepresentation = rolesResource.get(request.getRoleName()).toRepresentation();
        return roleMapper.mapToDto(roleRepresentation);
    }

    @Transactional
    public List<RoleResponse> addDefaultAndAdminRole(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();

        RoleRepresentation defaultRole = new RoleRepresentation();
        String defaultRoleName = tenant.getAbbreviatedName().toLowerCase() + "_default_role";
        defaultRole.setName(defaultRoleName);
        defaultRole.setDescription("All users have this role");
        defaultRole.singleAttribute("tenantId", tenant.getId().toString());

        RoleRepresentation adminRole = new RoleRepresentation();
        String adminRoleName = tenant.getAbbreviatedName().toLowerCase() + "_admin";
        adminRole.setName(adminRoleName);
        adminRole.setDescription("This role is exclusively for administrator users.");
        adminRole.singleAttribute("tenantId", tenant.getId().toString());

        List<RoleRepresentation> roleRepresentations = rolesResource.list();
        for (RoleRepresentation existingRole : roleRepresentations) {
            if (existingRole.getName().equals(defaultRoleName)) {
                throw new ResourceExistsException(
                        "Role with name '" + defaultRoleName + "' already exists");
            }
            if (existingRole.getName().equals(adminRoleName)) {
                throw new ResourceExistsException(
                        "Role with name '" + adminRoleName + "' already exists");
            }
        }
        rolesResource.create(defaultRole);
        rolesResource.create(adminRole);

        List<RoleResponse> roleResponses = new ArrayList<>();
        defaultRole = rolesResource.get(defaultRoleName).toRepresentation();
        adminRole = rolesResource.get(adminRoleName).toRepresentation();
        roleResponses.add(roleMapper.mapToDto(defaultRole));
        roleResponses.add(roleMapper.mapToDto(adminRole));
        return roleResponses;
    }


    public List<RoleResponse> getAllRoles(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        List<RoleRepresentation> roleRepresentations = rolesResource.list();
        List<RoleResponse> filteredRoles = new ArrayList<>();

        for (RoleRepresentation role : roleRepresentations) {
            String roleName = role.getName();
            RoleResource roleResource = rolesResource.get(roleName);
            RoleRepresentation roleRepresentation = roleResource.toRepresentation();

            Map<String, List<String>> attributes = roleRepresentation.getAttributes();
            if (attributes != null && attributes.containsKey("tenantId")) {
                String userTenantId = attributes.get("tenantId").get(0);
                if (userTenantId.equals(tenant.getId().toString())) {
                    filteredRoles.add(roleMapper.mapToDto(roleRepresentation));
                }
            }
        }
        return filteredRoles;
    }

    public RoleResponse getRoleByName(UUID tenantId,
                                      String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        RoleResource roleResource = rolesResource.get(roleName);
        RoleRepresentation roleRepresentation = roleResource.toRepresentation();
        Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString())) {
            return roleMapper.mapToDto(roleRepresentation);
        } else {
            throw new ResourceNotFoundException(
                    "Role not found with name '" + roleName + "'");
        }
    }

    @Transactional
    public RoleResponse updateRole(UUID tenantId,
                                   String roleName,
                                   RoleRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        RoleResource roleResource = rolesResource.get(roleName);
        RoleRepresentation roleRepresentation = roleResource.toRepresentation();
        roleRepresentation = roleMapper.updateEntity(tenant, roleRepresentation, request);
        Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString())) {
            roleResource.update(roleRepresentation);
            return roleMapper.mapToDto(roleRepresentation);
        } else {
            throw new ResourceNotFoundException(
                    "Role not found with name '" + roleName + "'");
        }
    }

    @Transactional
    public RoleResponse assignRoleToUser(UUID tenantId,
                                         String userId,
                                         String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = userService.getUsersResource();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString())) {
            RolesResource rolesResource = getRolesResource();
            RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
            return roleMapper.mapToDto(roleRepresentation);
        } else {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "'");
        }
    }

    @Transactional
    public void unAssignRoleFromUser(UUID tenantId, String userId, String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = userService.getUsersResource();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(value -> value.get(0));

        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "'");
        }
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
        if (roleRepresentation == null) {
            throw new ResourceNotFoundException("Role not found with name '" + roleName + "'");
        }
        if (!userResource.roles().realmLevel().listEffective().contains(roleRepresentation)) {
            throw new ResourceNotFoundException(
                    "User does not have role '" + roleName + "'");
        }
        userResource.roles().realmLevel().remove(Collections.singletonList(roleRepresentation));
    }

    @Transactional
    public void deleteRole(UUID tenantId,
                           String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = getRolesResource();
        RoleResource roleResource = rolesResource.get(roleName);
        RoleRepresentation roleRepresentation = roleResource.toRepresentation();
        Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString())) {
            rolesResource.deleteRole(roleRepresentation.getName());
        } else {
            throw new ResourceNotFoundException(
                    "Role not found with name '" + roleName + "'");
        }
    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }
}
