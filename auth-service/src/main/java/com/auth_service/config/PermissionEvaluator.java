package com.auth_service.config;

import com.auth_service.enums.AuthServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final RoleConverter roleConverter;

    /* User Permission */
    public void addUserPermission() {
        checkPermission(AuthServiceResourceName.ADD_USER);
    }

    public void getAllUsersPermission() {
        checkPermission(AuthServiceResourceName.GET_ALL_USERS);
    }

    public void getUserRolesPermission() {
        checkPermission(AuthServiceResourceName.GET_USER_ROLES);
    }

    public void getUserByIdPermission() {
        checkPermission(AuthServiceResourceName.GET_USER_BY_ID);
    }

    public void updateUserPermission() {
        checkPermission(AuthServiceResourceName.UPDATE_USER);
    }

    public void deleteUserPermission() {
        checkPermission(AuthServiceResourceName.DELETE_USER);
    }

    /* Role Permission */
    public void addRolePermission() {
        checkPermission(AuthServiceResourceName.ADD_ROLE);
    }

    public void assignRoleToUserPermission() {
        checkPermission(AuthServiceResourceName.ASSIGN_ROLE_TO_USER);
    }

    public void getAllRolesPermission() {
        checkPermission(AuthServiceResourceName.GET_ALL_ROLES);
    }

    public void getRoleByNamePermission() {
        checkPermission(AuthServiceResourceName.GET_ROLE_BY_NAME);
    }

    public void updateRolePermission() {
        checkPermission(AuthServiceResourceName.UPDATE_ROLE);
    }

    public void unassignRoleFromUserPermission() {
        checkPermission(AuthServiceResourceName.UNASSIGN_ROLE_FROM_USER);
    }

    public void deleteRolePermission() {
        checkPermission(AuthServiceResourceName.DELETE_ROLE);
    }

    /* Resource Permission */

    public void giveResourceAccessToRolePermission() {
        checkPermission(AuthServiceResourceName.ASSIGN_ROLE_TO_RESOURCE);
    }

    public void getAllResourcesPermission() {
        if (!roleConverter.isAdmin()) {
            checkPermission(AuthServiceResourceName.GET_ALL_RESOURCES);
        }
    }

    public void getResourcesByRoleNamePermission() {
        checkPermission(AuthServiceResourceName.GET_RESOURCES_BY_ROLE_NAME);
    }

    public void getResourceByIdPermission() {
        boolean isAdmin = roleConverter.isAdmin();
        if (!isAdmin) {
            checkPermission(AuthServiceResourceName.GET_RESOURCE_BY_ID);
        }
    }

    public void getResourceByNamePermission() {
        boolean isAdmin = roleConverter.isAdmin();
        if (!isAdmin) {
            checkPermission(AuthServiceResourceName.GET_RESOURCE_BY_NAME);
        }
    }

    public void getResourcesByGroupPermission() {
        boolean isAdmin = roleConverter.isAdmin();
        if (!isAdmin) {
            checkPermission(AuthServiceResourceName.GET_RESOURCES_BY_GROUP);
        }
    }

    public void removeResourceAccessFromRolePermission() {
        checkPermission(AuthServiceResourceName.UNASSIGN_ROLE_FROM_RESOURCE);
    }

    private void checkPermission(AuthServiceResourceName resourceName) {
        boolean hasPermission = roleConverter.hasPermission(resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}