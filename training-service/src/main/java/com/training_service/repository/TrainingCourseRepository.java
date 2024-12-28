package com.training_service.repository;

import com.training_service.model.TrainingCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingCourseRepository extends JpaRepository<TrainingCourse, UUID> {

    boolean existsByTenantIdAndCourseName(UUID tenantId, String courseName);
}
