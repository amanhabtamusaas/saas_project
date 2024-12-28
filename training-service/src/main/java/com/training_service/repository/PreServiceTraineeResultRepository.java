package com.training_service.repository;

import com.training_service.model.PreServiceTraineeResult;
import com.training_service.enums.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PreServiceTraineeResultRepository extends JpaRepository<PreServiceTraineeResult, UUID> {

    List<PreServiceTraineeResult> findByTenantIdAndPreServiceCourseId(UUID tenantId, UUID courseId);
    boolean existsByTenantIdAndPreServiceTraineeIdAndPreServiceCourseIdAndSemester(
            UUID tenantId, UUID preServiceTraineeId, UUID preServiceCourseId, Semester semester);
}
