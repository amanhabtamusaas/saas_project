package com.insa.mapper;

import com.insa.dto.request.TrainingCourseRequest;
import com.insa.dto.response.TrainingCourseResponse;
import com.insa.entity.TrainingCourse;
import org.springframework.stereotype.Component;

@Component
public class TrainingCourseMapper {

    public TrainingCourse mapToEntity(TrainingCourseRequest request) {

        TrainingCourse trainingCourse = new TrainingCourse();
        trainingCourse.setCourseName(request.getCourseName());
        trainingCourse.setDescription(request.getDescription());

        return trainingCourse;
    }

    public TrainingCourseResponse mapToDto(TrainingCourse trainingCourse) {

        TrainingCourseResponse response = new TrainingCourseResponse();
        response.setId(trainingCourse.getId());
        response.setCourseName(trainingCourse.getCourseName());
        response.setDescription(trainingCourse.getDescription());
        response.setCourseCategoryId(trainingCourse.getTrainingCourseCategory().getId());
        response.setTenantId(trainingCourse.getTenantId());
        response.setCreatedAt(trainingCourse.getCreatedAt());
        response.setUpdatedAt(trainingCourse.getUpdatedAt());
        response.setCreatedBy(trainingCourse.getCreatedBy());
        response.setUpdatedBy(trainingCourse.getUpdatedBy());

        return response;
    }

    public TrainingCourse updateEntity(TrainingCourse trainingCourse,
                                       TrainingCourseRequest request) {

        if (request.getCourseName() != null) {
            trainingCourse.setCourseName(request.getCourseName());
        }
        if (request.getDescription() != null) {
            trainingCourse.setDescription(request.getDescription());
        }

        return trainingCourse;
    }
}
