package com.auth_service.service;

import com.auth_service.dto.clientDto.EmployeeDto;
import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.dto.request.ResetPasswordRequest;
import com.auth_service.dto.response.RoleResponse;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.exception.ResourceNotFoundException;
import com.auth_service.mapper.RoleMapper;
import com.auth_service.mapper.UserMapper;
import com.auth_service.utility.ValidationUtil;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final Keycloak keycloak;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final ValidationUtil validationUtil;

    @Value("${keycloak.realm}")
    private String realm;

    @Transactional
    public UserResponse addUser(UUID tenantId,
                                String employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EmployeeDto employee = validationUtil.getEmployeeByEmployeeId(tenant.getId(), employeeId);
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = userMapper.mapToEntity(tenant, employeeId);
        List<UserRepresentation> existedUser = usersResource
                .searchByUsername(employee.getEmployeeId(), true);
        if (!existedUser.isEmpty() && existedUser.get(0) != null) {
            throw new ResourceExistsException(
                    "A user with the username '" + employee.getEmployeeId() + "' already exists.");
        }
        Response response = usersResource.create(userRepresentation);
        if (!Objects.equals(201, response.getStatus())) {
            throw new RuntimeException("Failed to create new user");
        }
        List<UserRepresentation> createdUser = usersResource
                .searchByUsername(employee.getEmployeeId(), true);
        userRepresentation = createdUser.get(0);
        RolesResource rolesResource = getRolesResource();
        String roleName = tenant.getAbbreviatedName().toLowerCase() + "_default_role";
        RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));

        return userMapper.mapToDto(userRepresentation);
    }

    @Transactional
    public UserResponse addAdminUser(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        String username = tenant.getAbbreviatedName().toLowerCase() + "_admin";
        UserRepresentation userRepresentation = userMapper.mapAdminUser(tenant, username);
        List<UserRepresentation> existedUser = usersResource.searchByUsername(username, true);
        if (!existedUser.isEmpty() && existedUser.get(0) != null) {
            throw new ResourceExistsException(
                    "A user with the username '" + username + "' already exists.");
        }
        Response response = usersResource.create(userRepresentation);
        if (!Objects.equals(201, response.getStatus())) {
            throw new RuntimeException("Failed to create administrator user");
        }
        List<UserRepresentation> createdUser = usersResource.searchByUsername(username, true);
        userRepresentation = createdUser.get(0);
        RolesResource rolesResource = getRolesResource();
        String roleName = tenant.getAbbreviatedName().toLowerCase() + "_admin";
        RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));

        return userMapper.mapToDto(userRepresentation);
    }

    public List<UserResponse> getAllUsers(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.list();
        return userRepresentations.stream()
                .filter(user -> {
                    Optional<String> userTenantId = Optional.ofNullable(user.getAttributes())
                            .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
                    return userTenantId.isPresent() && userTenantId.get().equals(tenant.getId().toString());
                })
                .map(userMapper::mapToDto)
                .toList();
    }

    public UserResponse getUserById(UUID tenantId, String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "'");
        }
        return userMapper.mapToDto(userRepresentation);
    }

    public UserResponse getUserByUsername(UUID tenantId,
                                          String username) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation = userRepresentations.get(0);
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with username '" + username + "'");
        }
        return userMapper.mapToDto(userRepresentation);
    }

    public List<RoleResponse> getUserRoles(UUID tenantId,
                                           String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "'");
        }
        List<RoleRepresentation> roleRepresentations = userResource.roles().realmLevel().listAll();
        return roleRepresentations.stream()
                .map(roleMapper::mapToDto)
                .toList();
    }

    @Transactional
    public UserResponse updateUser(UUID tenantId,
                                   String userId,
                                   UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userRepresentation = userMapper.updateEntity(tenant.getId(), userRepresentation, employeeId);
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "'");
        }
        userResource.update(userRepresentation);
        return userMapper.mapToDto(userRepresentation);
    }

    @Transactional
    public UserResponse addEmailToUser(UUID tenantId,
                                       String userId,
                                       String email) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "'");
        }
        if (email != null) {
            userRepresentation.setEmail(email);
        }
        userResource.update(userRepresentation);
        userRepresentation = usersResource.get(userId).toRepresentation();
        return userMapper.mapToDto(userRepresentation);
    }

    public void sendVerificationEmail(String email) {

        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByEmail(email, true);
        UserRepresentation userRepresentation = userRepresentations.get(0);
        if (userRepresentation == null) {
            throw new ResourceNotFoundException(
                    "User not found with email '" + email + "'");
        }
        usersResource.get(userRepresentation.getId()).sendVerifyEmail();
    }

    public void forgotPassword(UUID tenantId,
                               String email) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByEmail(email, true);
        if (userRepresentations.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email '" + email + "'");
        }
        UserRepresentation userRepresentation = userRepresentations.get(0);
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with email '" + email + "'");
        }
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    @Transactional
    public void resetUserPassword(UUID tenantId,
                                  String userId,
                                  ResetPasswordRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "'");
        }
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        userResource.resetPassword(credential);
    }

    @Transactional
    public void deleteUser(UUID tenantId,
                           String userId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        UsersResource usersResource = getUsersResource();
        UserResource userResource = usersResource.get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        Optional<String> userTenantId = Optional.ofNullable(userRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (userTenantId.isEmpty() || !userTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "User not found with id '" + userId + "'");
        }
        usersResource.delete(userRepresentation.getId());
    }

    public UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }
}