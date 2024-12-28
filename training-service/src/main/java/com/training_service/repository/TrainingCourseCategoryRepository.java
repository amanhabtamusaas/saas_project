package com.training_service.repository;

import com.training_service.model.TrainingCourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingCourseCategoryRepository extends JpaRepository<TrainingCourseCategory, UUID> {

    boolean existsByTenantIdAndCategoryName(UUID tenantId, String categoryName);
}
