package com.auth_service.mapper;

import com.auth_service.dto.clientDto.EmployeeDto;
import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.client.EmployeeServiceClient;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final EmployeeServiceClient employeeServiceClient;

    public UserRepresentation mapToEntity(TenantDto tenant,
                                          String employeeId) {

        EmployeeDto employee = employeeServiceClient
                .getEmployeeByEmployeeId(tenant.getId(), employeeId);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.singleAttribute("tenantId", tenant.getId().toString());
        userRepresentation.setUsername(employee.getEmployeeId());
        userRepresentation.setFirstName(employee.getFirstName());
        userRepresentation.setLastName(employee.getLastName());
        userRepresentation.setEmail("");
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(employee.getEmployeeId());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        return userRepresentation;
    }

    public UserResponse mapToDto(UserRepresentation userRepresentation) {

        UserResponse response = new UserResponse();
        response.setId(userRepresentation.getId());
        response.setTenantId(userRepresentation.getAttributes().get("tenantId").get(0));
        response.setFirstName(userRepresentation.getFirstName());
        response.setLastName(userRepresentation.getLastName());
        response.setEmail(userRepresentation.getEmail());
        response.setUsername(userRepresentation.getUsername());

        return response;
    }

    public UserRepresentation updateEntity(UUID tenantId,
                                           UserRepresentation userRepresentation,
                                           UUID employeeId) {

        EmployeeDto employee = employeeServiceClient
                .getEmployeeById(tenantId, employeeId);

        userRepresentation.setUsername(employee.getEmployeeId());
        userRepresentation.setFirstName(employee.getFirstName());
        userRepresentation.setLastName(employee.getLastName());

        return userRepresentation;
    }

    public UserRepresentation mapAdminUser(TenantDto tenant, String username) {

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(username);
        userRepresentation.setEmail("");
        userRepresentation.setFirstName(tenant.getAbbreviatedName());
        userRepresentation.setLastName("Admin");
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("tenantId", Collections.singletonList(tenant.getId().toString()));
        userRepresentation.setAttributes(attributes);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(username);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        return userRepresentation;
    }

}
