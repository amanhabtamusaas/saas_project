package com.auth_service.service;

import com.auth_service.config.RoleConverter;
import com.auth_service.data.*;
import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.dto.request.ResourceRequest;
import com.auth_service.dto.request.RolesRequest;
import com.auth_service.dto.response.ResourceResponse;
import com.auth_service.exception.ResourceNotFoundException;
import com.auth_service.mapper.ResourceMapper;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import com.auth_service.utility.ResourceUtil;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final ValidationUtil validationUtil;
    private final Keycloak keycloak;
    private final OrganizationServiceResource organizationServiceResource;
    private final EmployeeServiceResource employeeServiceResource;
    private final AuthServiceResource authServiceResource;
    private final LeaveServiceResource leaveServiceResource;
    private final RecruitmentServiceResource recruitmentServiceResource;
    private final TrainingServiceResource trainingServiceResource;
    private final HrPlanningServiceResource hrPlanningServiceResource;
    private final ResourceUtil resourceUtil;
    private final RoleConverter roleConverter;

    @Value("${keycloak.realm}")
    private String realm;

    @Transactional
    public List<ResourceResponse> addResource(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        organizationServiceResource.storeEndpoints(tenant);
        employeeServiceResource.storeEndpoints(tenant);
        authServiceResource.storeEndpoints(tenant);
        leaveServiceResource.storeEndpoints(tenant);
        recruitmentServiceResource.storeEndpoints(tenant);
        trainingServiceResource.storeEndpoints(tenant);
        hrPlanningServiceResource.storeEndpoints(tenant);

        List<Resource> resources = resourceRepository.findByTenantId(tenant.getId());
        return resources.stream()
                .map(resourceMapper::mapToDto)
                .toList();
    }

    public List<ResourceResponse> getAllResources(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Resource> resources = resourceRepository.findByTenantId(tenant.getId());
        return resources.stream()
                .map(resourceMapper::mapToDto)
                .toList();
    }

    public List<ResourceResponse> getResourcesNotContainDefaultRole(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Resource> resources = resourceRepository.findByTenantId(tenant.getId());
        String defaultRole = tenant.getAbbreviatedName().toLowerCase() + "_default_role";
        return resources.stream()
                .filter(r -> !r.getRequiredRoles().contains(defaultRole))
                .map(resourceMapper::mapToDto)
                .toList();
    }

    public ResourceResponse getResourceById(UUID tenantId,
                                            UUID resourceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        return resourceMapper.mapToDto(resource);
    }

    public ResourceResponse getResponseByName(UUID tenantId,
                                              String resourceName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findByTenantIdAndResourceName(tenant.getId(), resourceName)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with name '" + resourceName + "'"));
        return resourceMapper.mapToDto(resource);
    }

    public List<ResourceResponse> getResourcesByGroupName(UUID tenantId,
                                                          String groupName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Resource> resources = resourceRepository.findByTenantIdAndGroupName(tenant.getId(), groupName);
        return resources.stream()
                .map(resourceMapper::mapToDto)
                .toList();
    }

    @Transactional
    public ResourceResponse updateResource(UUID tenantId,
                                           UUID resourceId,
                                           ResourceRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        resource = resourceMapper.updateEntity(resource, request);
        resource = resourceRepository.save(resource);
        return resourceMapper.mapToDto(resource);
    }

    @Transactional
    public ResourceResponse giveResourceAccessToRole(UUID tenantId,
                                                  UUID resourceId,
                                                  RolesRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<RoleRepresentation> roleRepresentations = new HashSet<>();
            for (String role : request.getRoles()) {
                RolesResource rolesResource = keycloak.realm(realm).roles();
                RoleRepresentation roleRepresentation = rolesResource.get(role).toRepresentation();
                Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                        .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
                if (roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString())) {
                    roleRepresentations.add(roleRepresentation);
                } else {
                    throw new ResourceNotFoundException(
                            "Role not found with name '" + role + "'");
                }
            }
            Set<String> roles = roleRepresentations.stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toSet());
            resource.setRequiredRoles(roles);
            resource = resourceRepository.save(resource);
            resourceUtil.assignRoleToRelatedResources(tenant.getId(), resource.getResourceName(), roles);
            return resourceMapper.mapToDto(resource);
        } else {
            throw new IllegalArgumentException(
                    "You must assign at least one role to the resource.");
        }
    }

    public List<ResourceResponse> getResourcesByRole(UUID tenantId,
                                                     String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        RolesResource rolesResource = keycloak.realm(realm).roles();
        RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
        Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (roleTenantId.isPresent() && roleTenantId.get().equals(tenant.getId().toString())) {
            List<Resource> resources = resourceRepository.findAll();
            return resources.stream()
                    .filter(r -> r.getTenantId().equals(tenant.getId()))
                    .filter(r -> r.getRequiredRoles().contains(roleName))
                    .map(resourceMapper::mapToDto)
                    .toList();
        } else {
            throw new ResourceNotFoundException(
                    "Role not found with name '" + roleName + "'");
        }
    }

    @Transactional
    public ResourceResponse removeResourceAccessFromRole(UUID tenantId,
                                                         UUID resourceId,
                                                         String roleName) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        RolesResource rolesResource = keycloak.realm(realm).roles();
        RoleRepresentation roleRepresentation = rolesResource.get(roleName).toRepresentation();
        Optional<String> roleTenantId = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attr -> attr.get("tenantId")).map(list -> list.get(0));
        if (roleTenantId.isEmpty() || !roleTenantId.get().equals(tenant.getId().toString())) {
            throw new ResourceNotFoundException(
                    "Role not found with name '" + roleName + "'");
        }
        if (!resource.getRequiredRoles().contains(roleName)) {
            throw new ResourceNotFoundException("The specified role is not associated with the resource");
        }
        resource.getRequiredRoles().remove(roleRepresentation.getName());
        resource = resourceRepository.save(resource);
        return resourceMapper.mapToDto(resource);
    }

    @Transactional
    public void deleteResource(UUID tenantId,
                               UUID resourceId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Resource resource = resourceRepository.findById(resourceId)
                .filter(res -> res.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id '" + resourceId + "'"));
        resourceRepository.delete(resource);
    }

    @Transactional
    public void deleteAllResources(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Resource> resources = resourceRepository.findByTenantId(tenant.getId());
        resourceRepository.deleteAll(resources);
    }

//    private final Keycloak keycloak;
//    private final ResourceMapper resourceMapper;
//
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    @Value("${keycloak.client-id}")
//    private String clientId;
//
//    public ResourceResponse addResource(ResourceRequest request) {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        ResourceRepresentation resourceRepresentation = resourceMapper.mapToEntity(request);
//        try{
//            resourcesResource.create(resourceRepresentation);
//            log.info("New resource has been created");
////            ResourceRepresentation createdResource = resourcesResource.resources()
////                    .get(Integer.parseInt(resourceRepresentation.getId()));
//            return resourceMapper.mapToDto(resourceRepresentation);
//        } catch (Exception e){
//            log.error("Failed to create new resource: {}", e.getMessage());
//            throw new RuntimeException("Failed to create new resource ", e);
//        }
//    }
//
//    public List<ResourceResponse> getAllResources() {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        List<ResourceRepresentation> resourceRepresentations = resourcesResource.resources();
//        return resourceRepresentations.stream()
//                .map(resourceMapper::mapToDto)
//                .toList();
//    }
//
//    public ResourceResponse getResourceById(String resourceId) {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        ResourceRepresentation resourceRepresentation = resourcesResource.resource(resourceId).toRepresentation();
//        return resourceMapper.mapToDto(resourceRepresentation);
//    }
//
//    public ResourceResponse updateResource(String resourceId,
//                                           ResourceRequest request) {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        ResourceRepresentation resourceRepresentation = resourcesResource.resource(resourceId).toRepresentation();
//        resourceRepresentation = resourceMapper.updateEntity(resourceRepresentation, request);
//        resourcesResource.resource(resourceId).update(resourceRepresentation);
//        return resourceMapper.mapToDto(resourceRepresentation);
//    }
//
//    public void deleteResource(String resourceId) {
//
//        ResourcesResource resourcesResource = getResourcesResource();
//        ResourceRepresentation resourceRepresentation = resourcesResource.resource(resourceId).toRepresentation();
//        resourcesResource.resource(resourceRepresentation.getId()).remove();
//    }
//
//    private ResourcesResource getResourcesResource() {
//
//        RealmResource realmResource = keycloak.realm(realm);
//        ClientsResource clientsResource = realmResource.clients();
//        ClientRepresentation clientRepresentation = clientsResource.findByClientId(clientId).get(0);
//        ClientResource clientResource = clientsResource.get(clientRepresentation.getId());
//        AuthorizationResource authorizationResource = clientResource.authorization();
//        return authorizationResource.resources();
//    }
}
