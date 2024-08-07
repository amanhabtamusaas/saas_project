package com.insa.mapper;

import com.insa.dto.request.UserRequest;
import com.insa.dto.response.UserResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserRepresentation mapToEntity(UserRequest request) {

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(request.getFirstName());
        userRepresentation.setLastName(request.getLastName());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setUsername(request.getUsername());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        List<CredentialRepresentation> credentialRepresentations = new ArrayList<>();
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentations.add(credentialRepresentation);
        userRepresentation.setCredentials(credentialRepresentations);

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

    public UserRepresentation updateEntity(UserRepresentation userRepresentation,
                                           UserRequest request) {

        if (request.getFirstName() != null) {
            userRepresentation.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            userRepresentation.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            userRepresentation.setEmail(request.getEmail());
        }
        if (request.getUsername() != null) {
            userRepresentation.setUsername(request.getUsername());
        }

        return userRepresentation;
    }
}
