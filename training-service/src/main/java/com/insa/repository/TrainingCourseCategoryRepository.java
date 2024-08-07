package com.insa.repository;

import com.insa.entity.TrainingCourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingCourseCategoryRepository extends JpaRepository<TrainingCourseCategory, Long> {

    boolean existsByTenantIdAndCategoryName(Long tenantId, String categoryName);
}
