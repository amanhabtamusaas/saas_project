package com.insa.service;

import com.insa.dto.request.ResourceRequest;
import com.insa.dto.response.ResourceResponse;
import com.insa.mapper.ResourceMapper;
import com.insa.utility.KeycloakSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.AuthorizationResource;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.ResourcesResource;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final KeycloakSecurityUtil keycloakSecurityUtil;
    private final ResourceMapper resourceMapper;

    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;

    public ResourceResponse addResource(ResourceRequest request) {

        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        ClientResource clientResource = realmResource.clients().get(clientId);
        AuthorizationResource authorizationResource = clientResource.authorization();
        ResourcesResource resourcesResource = authorizationResource.resources();
        ResourceRepresentation resourceRepresentation = resourceMapper.mapToEntity(request);
        resourcesResource.create(resourceRepresentation);
        return resourceMapper.mapToDto(resourceRepresentation);
    }

    public List<ResourceResponse> getAllResources() {

        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        ClientResource clientResource = realmResource.clients().get(clientId);
        AuthorizationResource authorizationResource = clientResource.authorization();
        ResourcesResource resourcesResource = authorizationResource.resources();
        List<ResourceRepresentation> resourceRepresentations = resourcesResource.resources();
        return resourceRepresentations.stream()
                .map(resourceMapper::mapToDto)
                .toList();
    }

    public ResourceRepresentation getAuthorizationResource(String resourceName) {

        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        AuthorizationResource authorizationResource = realmResource.clients().get(clientId).authorization();
        return authorizationResource.resources().resource(resourceName).toRepresentation();
    }
}
