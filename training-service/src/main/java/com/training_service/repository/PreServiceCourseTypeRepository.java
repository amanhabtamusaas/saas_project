package com.training_service.repository;

import com.training_service.model.PreServiceCourseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PreServiceCourseTypeRepository extends JpaRepository<PreServiceCourseType, UUID> {

    boolean existsByTenantIdAndCourseType(UUID tenantId, String courseType);
}
