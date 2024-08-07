package com.insa.mapper;

import com.insa.dto.request.TrainingCourseCategoryRequest;
import com.insa.dto.response.TrainingCourseCategoryResponse;
import com.insa.entity.TrainingCourseCategory;
import org.springframework.stereotype.Component;

@Component
public class TrainingCourseCategoryMapper {

    public TrainingCourseCategory mapToEntity(TrainingCourseCategoryRequest request) {
        TrainingCourseCategory trainingCourseCategory = new TrainingCourseCategory();
        trainingCourseCategory.setCategoryName(request.getCategoryName());
        trainingCourseCategory.setDescription(request.getDescription());

        return trainingCourseCategory;
    }

    public TrainingCourseCategoryResponse mapToDto(TrainingCourseCategory trainingCourseCategory) {
        TrainingCourseCategoryResponse response = new TrainingCourseCategoryResponse();
        response.setId(trainingCourseCategory.getId());
        response.setCategoryName(trainingCourseCategory.getCategoryName());
        response.setDescription(trainingCourseCategory.getDescription());
        response.setTenantId(trainingCourseCategory.getTenantId());
        response.setCreatedAt(trainingCourseCategory.getCreatedAt());
        response.setUpdatedAt(trainingCourseCategory.getUpdatedAt());
        response.setCreatedBy(trainingCourseCategory.getCreatedBy());
        response.setUpdatedBy(trainingCourseCategory.getUpdatedBy());

        return response;
    }

    public TrainingCourseCategory updateEntity(TrainingCourseCategory trainingCourseCategory,
                                               TrainingCourseCategoryRequest request) {
        if (request.getCategoryName() != null) {
            trainingCourseCategory.setCategoryName(request.getCategoryName());
        }
        if (request.getDescription() != null) {
            trainingCourseCategory.setDescription(request.getDescription());
        }

        return trainingCourseCategory;
    }
}
