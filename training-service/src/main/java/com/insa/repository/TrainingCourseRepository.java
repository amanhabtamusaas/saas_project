package com.insa.repository;

import com.insa.entity.TrainingCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingCourseRepository extends JpaRepository<TrainingCourse, Long> {

    boolean existsByTenantIdAndCourseName(Long tenantId, String courseName);
}
