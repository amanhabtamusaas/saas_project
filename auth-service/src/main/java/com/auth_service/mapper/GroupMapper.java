package com.auth_service.mapper;

import com.auth_service.dto.request.GroupRequest;
import com.auth_service.dto.response.GroupResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {

    public GroupResponse mapToDto(GroupRepresentation groupRepresentation) {

        GroupResponse response = new GroupResponse();
        response.setId(groupRepresentation.getId());
        response.setGroupName(groupRepresentation.getName());

        return response;
    }
}
