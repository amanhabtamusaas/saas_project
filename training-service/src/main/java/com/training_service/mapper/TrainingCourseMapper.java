package com.training_service.mapper;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.TrainingCourseRequest;
import com.training_service.dto.response.TrainingCourseResponse;
import com.training_service.model.TrainingCourse;
import com.training_service.model.TrainingCourseCategory;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingCourseMapper {

    private final ValidationUtil validationUtil;

    public TrainingCourse mapToEntity(TenantDto tenant,
                                      TrainingCourseRequest request) {

        TrainingCourseCategory trainingCourseCategory = validationUtil
                .getCategoryById(tenant.getId(), request.getCourseCategoryId());

        TrainingCourse trainingCourse = new TrainingCourse();
        trainingCourse.setTenantId(tenant.getId());
        trainingCourse.setTrainingCourseCategory(trainingCourseCategory);
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

    public TrainingCourse updateEntity(TenantDto tenant,
                                       TrainingCourse trainingCourse,
                                       TrainingCourseRequest request) {

        TrainingCourseCategory trainingCourseCategory = validationUtil
                .getCategoryById(tenant.getId(), request.getCourseCategoryId());

        if (request.getCourseCategoryId()!= null) {
            trainingCourse.setTrainingCourseCategory(trainingCourseCategory);
        }
        if (request.getCourseName() != null) {
            trainingCourse.setCourseName(request.getCourseName());
        }
        if (request.getDescription() != null) {
            trainingCourse.setDescription(request.getDescription());
        }

        return trainingCourse;
    }
}
