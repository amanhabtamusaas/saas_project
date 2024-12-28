package com.auth_service.mapper;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.dto.request.ResourceRequest;
import com.auth_service.dto.response.ResourceResponse;
import com.auth_service.model.Resource;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceMapper {

    public Resource mapToEntity(TenantDto tenant,
                                ResourceRequest request) {

        Resource resource = new Resource();
        resource.setTenantId(tenant.getId());
        resource.setResourceName(request.getResourceName());
        resource.setGroupName(request.getGroupName());
        resource.setDescription(request.getDescription());

        return resource;
    }

    public ResourceResponse mapToDto(Resource resource) {

        ResourceResponse response = new ResourceResponse();
        response.setId(resource.getId());
        response.setResourceName(resource.getResourceName());
        response.setGroupName(resource.getGroupName());
        response.setRequiredRoles(resource.getRequiredRoles());
        response.setDescription(resource.getDescription());
        response.setTenantId(resource.getTenantId());
        response.setCreatedAt(resource.getCreatedAt());
        response.setCreatedBy(resource.getCreatedBy());
        response.setUpdatedAt(resource.getUpdatedAt());
        response.setUpdatedBy(resource.getUpdatedBy());

        return response;
    }

    public Resource updateEntity(Resource resource, ResourceRequest request) {

        if (request.getResourceName() != null) {
            resource.setResourceName(request.getResourceName());
        }
        if (request.getGroupName() != null) {
            resource.setGroupName(request.getGroupName());
        }
        if (request.getDescription() != null) {
            resource.setDescription(request.getDescription());
        }

        return resource;
    }

//    public ResourceRepresentation mapToEntity(ResourceRequest request) {
//
//        ResourceRepresentation resourceRepresentation = new ResourceRepresentation();
//        resourceRepresentation.setName(request.getResourceName());
//        resourceRepresentation.setDisplayName(request.getResourceName());
//        resourceRepresentation.setUris(request.getUris());
//
//        return resourceRepresentation;
//    }
//
//    public ResourceResponse mapToDto(ResourceRepresentation resourceRepresentation) {
//
//        ResourceResponse response = new ResourceResponse();
//        response.setId(resourceRepresentation.getId());
//        response.setResourceName(resourceRepresentation.getName());
//        response.setUris(resourceRepresentation.getUris());
//
//        return response;
//    }
//
//    public ResourceRepresentation updateEntity(ResourceRepresentation resourceRepresentation,
//                                               ResourceRequest request) {
//
//        if (request.getResourceName() != null) {
//            resourceRepresentation.setName(request.getResourceName());
//            resourceRepresentation.setDisplayName(request.getResourceName());
//        }
//        if (request.getUris() != null) {
//            resourceRepresentation.setUris(request.getUris());
//        }
//
//        return resourceRepresentation;
//    }
}
