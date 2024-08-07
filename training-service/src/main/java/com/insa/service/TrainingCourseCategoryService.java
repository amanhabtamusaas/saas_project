package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.TrainingCourseCategoryRequest;
import com.insa.dto.response.TrainingCourseCategoryResponse;
import com.insa.entity.TrainingCourseCategory;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TrainingCourseCategoryMapper;
import com.insa.repository.TrainingCourseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingCourseCategoryService {

    private final TrainingCourseCategoryRepository trainingCourseCategoryRepository;
    private final TenantServiceClient tenantServiceClient;
    private final TrainingCourseCategoryMapper trainingCourseCategoryMapper;

    @Transactional
    public TrainingCourseCategoryResponse addCourseCategory(Long tenantId,
                                                            TrainingCourseCategoryRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryMapper.mapToEntity(request);
        trainingCourseCategory.setTenantId(tenant.getId());
        if (trainingCourseCategoryRepository.existsByTenantIdAndCategoryName(
                tenant.getId(), request.getCategoryName())) {
            throw new ResourceExistsException(
                    "Course category with Name '" + trainingCourseCategory.getCategoryName () + "' already exists");
        }
        trainingCourseCategory = trainingCourseCategoryRepository.save(trainingCourseCategory);
        return trainingCourseCategoryMapper.mapToDto(trainingCourseCategory);
    }

    public List<TrainingCourseCategoryResponse> getAllCourseCategories(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<TrainingCourseCategory> courseCategories = trainingCourseCategoryRepository.findAll();
        return courseCategories.stream()
                .filter(category -> category.getTenantId().equals(tenant.getId ()))
                .map(trainingCourseCategoryMapper::mapToDto)
                .toList();
    }

    public TrainingCourseCategoryResponse getCourseCategoryById(Long tenantId,
                                                                Long categoryId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository.findById(categoryId)
                .filter(category -> category.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + categoryId + "' in the specified tenant"));
        return trainingCourseCategoryMapper.mapToDto(trainingCourseCategory);
    }

    @Transactional
    public TrainingCourseCategoryResponse updateCourseCategory(Long tenantId,
                                                               Long categoryId,
                                                               TrainingCourseCategoryRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository.findById(categoryId)
                .filter(category -> category.getTenantId().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + categoryId + "' in the specified tenant"));
        trainingCourseCategory = trainingCourseCategoryMapper.updateEntity(trainingCourseCategory, request);
        trainingCourseCategory = trainingCourseCategoryRepository.save(trainingCourseCategory);
        return trainingCourseCategoryMapper.mapToDto(trainingCourseCategory);
    }

    @Transactional
    public void deleteCourseCategory(Long tenantId,
                                     Long categoryId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository.findById(categoryId)
                .filter(category -> category.getTenantId().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + categoryId + "' in the specified tenant"));
        trainingCourseCategoryRepository.delete(trainingCourseCategory);
    }
}
