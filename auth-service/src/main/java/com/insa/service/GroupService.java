package com.insa.service;

import com.insa.dto.request.GroupRequest;
import com.insa.dto.response.GroupResponse;
import com.insa.mapper.GroupMapper;
import com.insa.utility.KeycloakSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final KeycloakSecurityUtil keycloakSecurityUtil;
    private final GroupMapper groupMapper;

    @Value("${keycloak.realm}")
    private String realm;

    public GroupResponse addGroup(GroupRequest request) {

        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        GroupsResource groupsResource = realmResource.groups();
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(request.getGroupName());
        groupsResource.add(groupRepresentation);
        return groupMapper.mapToDto(groupRepresentation);
    }

    public List<GroupResponse> getAllGroups() {

        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        List<GroupRepresentation> groupRepresentations = groupsResource.groups();
        return groupRepresentations.stream()
                .map(groupMapper::mapToDto)
                .toList();
    }

    public GroupResponse getGroupId(String groupId) {

        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupResource groupResource = groupsResource.group(groupId);
        GroupRepresentation groupRepresentation = groupResource.toRepresentation();
        return groupMapper.mapToDto(groupRepresentation);
    }

    public GroupResponse updateGroup(String groupId, GroupRequest request) {

        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupResource groupResource = groupsResource.group(groupId);
        GroupRepresentation groupRepresentation = groupResource.toRepresentation();
        if (request.getGroupName() != null) {
            groupRepresentation.setName(request.getGroupName());
        }
        groupResource.update(groupRepresentation);
        return groupMapper.mapToDto(groupRepresentation);
    }

    public void deleteGroup(String groupId) {

        Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupResource groupResource = groupsResource.group(groupId);
        groupResource.remove();
    }
}
