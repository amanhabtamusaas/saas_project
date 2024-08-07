package com.insa.mapper;

import com.insa.dto.request.ResourceRequest;
import com.insa.dto.response.ResourceResponse;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

    public ResourceRepresentation mapToEntity(ResourceRequest request) {

        ResourceRepresentation resourceRepresentation = new ResourceRepresentation();
        resourceRepresentation.setName(request.getResourceName());
        resourceRepresentation.setDisplayName(request.getResourceName());
        resourceRepresentation.setUris(request.getUris());

        return resourceRepresentation;
    }

    public ResourceResponse mapToDto(ResourceRepresentation resourceRepresentation) {

        ResourceResponse response = new ResourceResponse();
        response.setId(resourceRepresentation.getId());
        response.setResourceName(resourceRepresentation.getName());
        response.setUris(resourceRepresentation.getUris());

        return response;
    }

    public ResourceRepresentation updateEntity(ResourceRepresentation resourceRepresentation,
                                               ResourceRequest request) {

        if (request.getResourceName() != null) {
            resourceRepresentation.setName(request.getResourceName());
            resourceRepresentation.setDisplayName(request.getResourceName());
        }
        if (request.getUris() != null) {
            resourceRepresentation.setUris(request.getUris());
        }

        return resourceRepresentation;
    }
}
