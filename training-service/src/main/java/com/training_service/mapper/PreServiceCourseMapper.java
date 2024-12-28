package com.training_service.mapper;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.PreServiceCourseRequest;
import com.training_service.dto.response.PreServiceCourseResponse;
import com.training_service.model.PreServiceCourse;
import com.training_service.model.PreServiceCourseType;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreServiceCourseMapper {

    private final ValidationUtil validationUtil;

    public PreServiceCourse mapToEntity(TenantDto tenant,
                                        PreServiceCourseRequest request) {

        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), request.getCourseTypeId());

        PreServiceCourse preServiceCourse = new PreServiceCourse();
        preServiceCourse.setTenantId(tenant.getId());
        preServiceCourse.setPreServiceCourseType(preServiceCourseType);
        preServiceCourse.setCourseName(request.getCourseName());
        preServiceCourse.setDescription(request.getDescription());

        return preServiceCourse;
    }

    public PreServiceCourseResponse mapToDto(PreServiceCourse preServiceCourse) {

        PreServiceCourseResponse response = new PreServiceCourseResponse();
        response.setId(preServiceCourse.getId());
        response.setCourseTypeId(preServiceCourse.getPreServiceCourseType().getId());
        response.setCourseName(preServiceCourse.getCourseName());
        response.setDescription(preServiceCourse.getDescription());
        response.setTenantId(preServiceCourse.getTenantId());
        response.setCreatedAt(preServiceCourse.getCreatedAt());
        response.setUpdatedAt(preServiceCourse.getUpdatedAt());
        response.setCreatedBy(preServiceCourse.getCreatedBy());
        response.setUpdatedBy(preServiceCourse.getUpdatedBy());

        return response;
    }

    public PreServiceCourse updateEntity(TenantDto tenant,
                                         PreServiceCourse preServiceCourse,
                                         PreServiceCourseRequest request) {

        PreServiceCourseType preServiceCourseType = validationUtil
                .getCourseTypeById(tenant.getId(), request.getCourseTypeId());

        if (request.getCourseTypeId() != null) {
            preServiceCourse.setPreServiceCourseType(preServiceCourseType);
        }
        if (request.getCourseName() != null) {
            preServiceCourse.setCourseName(request.getCourseName());
        }
        if (request.getDescription() != null) {
            preServiceCourse.setDescription(request.getDescription());
        }

        return preServiceCourse;
    }

}
