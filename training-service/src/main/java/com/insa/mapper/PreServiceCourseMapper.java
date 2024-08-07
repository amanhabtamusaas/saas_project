package com.insa.mapper;

import com.insa.dto.request.PreServiceCourseRequest;
import com.insa.dto.response.PreServiceCourseResponse;
import com.insa.entity.PreServiceCourse;
import org.springframework.stereotype.Component;

@Component
public class PreServiceCourseMapper {

    public PreServiceCourse mapToEntity(PreServiceCourseRequest request) {

        PreServiceCourse preServiceCourse = new PreServiceCourse();
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

    public PreServiceCourse updateEntity(PreServiceCourse preServiceCourse,
                                         PreServiceCourseRequest request) {

        if (request.getCourseName() != null) {
            preServiceCourse.setCourseName(request.getCourseName());
        }
        if (request.getDescription() != null) {
            preServiceCourse.setDescription(request.getDescription());
        }

        return preServiceCourse;
    }

}
