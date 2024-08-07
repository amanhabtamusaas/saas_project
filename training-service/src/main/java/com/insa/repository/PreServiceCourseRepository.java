package com.insa.repository;

import com.insa.entity.PreServiceCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreServiceCourseRepository extends JpaRepository<PreServiceCourse, Long> {

    boolean existsByTenantIdAndCourseName(Long tenantId, String courseName);
    List<PreServiceCourse> findByTenantIdAndPreServiceTraineesId(Long tenantId,Long traineeId);
    List<PreServiceCourse> findByTenantIdAndPreServiceCourseTypeId(Long tenantId, Long courseTypeId);
}
