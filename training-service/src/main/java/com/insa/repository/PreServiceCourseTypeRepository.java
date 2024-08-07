package com.insa.repository;

import com.insa.entity.PreServiceCourseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreServiceCourseTypeRepository extends JpaRepository<PreServiceCourseType, Long> {

    boolean existsByTenantIdAndCourseType(Long tenantId, String courseType);
}
