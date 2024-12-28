package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.TrainingCourseCategoryRequest;
import com.training_service.dto.response.TrainingCourseCategoryResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.model.TrainingCourseCategory;
import com.training_service.mapper.TrainingCourseCategoryMapper;
import com.training_service.repository.TrainingCourseCategoryRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingCourseCategoryService {

    private final TrainingCourseCategoryRepository trainingCourseCategoryRepository;
    private final TrainingCourseCategoryMapper trainingCourseCategoryMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public TrainingCourseCategoryResponse addCourseCategory(UUID tenantId,
                                                            TrainingCourseCategoryRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourseCategory category = trainingCourseCategoryMapper.mapToEntity(tenant, request);
        if (trainingCourseCategoryRepository.existsByTenantIdAndCategoryName(
                tenant.getId(), request.getCategoryName())) {
            throw new ResourceExistsException(
                    "Course category with Name '" + category.getCategoryName() + "' already exists");
        }
        category = trainingCourseCategoryRepository.save(category);
        return trainingCourseCategoryMapper.mapToDto(category);
    }

    public List<TrainingCourseCategoryResponse> getAllCourseCategories(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<TrainingCourseCategory> courseCategories = trainingCourseCategoryRepository.findAll();
        return courseCategories.stream()
                .filter(category -> category.getTenantId().equals(tenant.getId()))
                .map(trainingCourseCategoryMapper::mapToDto)
                .toList();
    }

    public TrainingCourseCategoryResponse getCourseCategoryById(UUID tenantId,
                                                                UUID categoryId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourseCategory category = validationUtil.getCategoryById(tenant.getId(), categoryId);
        return trainingCourseCategoryMapper.mapToDto(category);
    }

    @Transactional
    public TrainingCourseCategoryResponse updateCourseCategory(UUID tenantId,
                                                               UUID categoryId,
                                                               TrainingCourseCategoryRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourseCategory category = validationUtil.getCategoryById(tenant.getId(), categoryId);
        category = trainingCourseCategoryMapper.updateEntity(category, request);
        category = trainingCourseCategoryRepository.save(category);
        return trainingCourseCategoryMapper.mapToDto(category);
    }

    @Transactional
    public void deleteCourseCategory(UUID tenantId,
                                     UUID categoryId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TrainingCourseCategory category = validationUtil.getCategoryById(tenant.getId(), categoryId);
        trainingCourseCategoryRepository.delete(category);
    }
}