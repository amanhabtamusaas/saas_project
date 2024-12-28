package com.training_service.repository;

import com.training_service.model.PreServiceCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PreServiceCourseRepository extends JpaRepository<PreServiceCourse, UUID> {

    boolean existsByTenantIdAndCourseName(UUID tenantId, String courseName);
    List<PreServiceCourse> findByTenantIdAndPreServiceTraineesId(UUID tenantId,UUID traineeId);
    List<PreServiceCourse> findByTenantIdAndPreServiceCourseTypeId(UUID tenantId, UUID courseTypeId);
}
