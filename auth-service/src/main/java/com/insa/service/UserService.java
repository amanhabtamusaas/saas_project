package com.insa.service;

import com.insa.dto.TenantDto;
import com.insa.dto.request.UserRequest;
import com.insa.dto.response.RoleResponse;
import com.insa.dto.response.UserResponse;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.RoleMapper;
import com.insa.mapper.UserMapper;
import com.insa.utility.KeycloakSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KeycloakSecurityUtil keycloakSecurityUtil;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final TenantServiceClient tenantServiceClient;

    @Value("${keycloak.realm}")
    private String realm;

    public UserResponse addUser(Long tenantId, UserRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserRepresentation userRepresentation = userMapper.mapToEntity(request);
        userRepresentation.singleAttribute("tenantId", tenant.getId().toString());
        usersResource.create(userRepresentation);
        return userMapper.mapToDto(userRepresentation);
    }

    public List<UserResponse> getAllUsers(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> userRepresentations = usersResource.list();
        return userRepresentations.stream()
                .filter(user -> {
                    Optional<String> userTenantId = Optional.ofNullable(user.getAttributes())
                            .map(attr -> attr.get("tenantId"))
                            .map(list -> list.get(0));
                    return userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString());
                })
                .map(userMapper::mapToDto)
                .toList();
    }

    public UserResponse getUserById(Long tenantId, String userId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString())) {
            return userMapper.mapToDto(userRepresentation);
        } else {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "' in the specified tenant");
        }
    }

    public UserResponse updateUser(Long tenantId,
                                   String userId,
                                   UserRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation = userMapper.updateEntity(userRepresentation, request);
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString())) {
            userResource.update(userRepresentation);
            return userMapper.mapToDto(userRepresentation);
        } else {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "' in the specified tenant");
        }
    }

    public RoleResponse assignRoleToUser(Long tenantId,
                                         String userId,
                                         String roleName) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString())) {
            RolesResource rolesResource = realmResource.roles();
            RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
            return roleMapper.mapToDto(roleRepresentation);
        } else {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "' in the specified tenant");
        }
    }

    public List<RoleResponse> getUserRoles(Long tenantId,
                                           String userId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString())) {
            List<RoleRepresentation> roleRepresentations = userResource.roles().realmLevel().listAll();
            return roleRepresentations.stream()
                    .map(roleMapper::mapToDto)
                    .toList();
        } else {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "' in the specified tenant");
        }
    }

    public void removeRoleFromUser(Long tenantId,
                                   String userId,
                                   String roleName) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString())) {
            RolesResource rolesResource = realmResource.roles();
            RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
            userResource.roles().realmLevel().remove(Collections.singletonList(roleRepresentation));
        } else {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "' in the specified tenant");
        }
    }

    public void deleteUser(Long tenantId,
                           String userId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId"))
                .map(list -> list.get(0));
        if (userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString())) {
            usersResource.delete(userRepresentation.getId());
        } else {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "' in the specified tenant");
        }
    }
}
