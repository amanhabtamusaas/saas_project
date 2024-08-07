package com.insa.mapper;

import com.insa.dto.request.PreServiceCourseTypeRequest;
import com.insa.dto.response.PreServiceCourseTypeResponse;
import com.insa.entity.PreServiceCourseType;
import org.springframework.stereotype.Component;

@Component
public class PreServiceCourseTypeMapper {

    public PreServiceCourseType mapToEntity(PreServiceCourseTypeRequest request) {

        PreServiceCourseType preServiceCourseType = new PreServiceCourseType();
        preServiceCourseType.setCourseType(request.getCourseType());
        preServiceCourseType.setDescription(request.getDescription());

        return preServiceCourseType;
    }

    public PreServiceCourseTypeResponse mapToDto(PreServiceCourseType preServiceCourseType) {

        PreServiceCourseTypeResponse response = new PreServiceCourseTypeResponse();
        response.setId(preServiceCourseType.getId());
        response.setCourseType(preServiceCourseType.getCourseType());
        response.setDescription(preServiceCourseType.getDescription());
        response.setTenantId(preServiceCourseType.getTenantId());
        response.setCreatedAt(preServiceCourseType.getCreatedAt());
        response.setUpdatedAt(preServiceCourseType.getUpdatedAt());
        response.setCreatedBy(preServiceCourseType.getCreatedBy());
        response.setUpdatedBy(preServiceCourseType.getUpdatedBy());

        return response;
    }

    public PreServiceCourseType updateEntity(PreServiceCourseType preServiceCourseType, PreServiceCourseTypeRequest request) {

        if (request.getCourseType() != null) {
            preServiceCourseType.setCourseType(request.getCourseType());
        }
        if (request.getDescription() != null) {
            preServiceCourseType.setDescription(request.getDescription());
        }

        return preServiceCourseType;
    }
}
