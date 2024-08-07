package com.insa.repository;

import com.insa.entity.PreServiceTraineeResult;
import com.insa.enums.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreServiceTraineeResultRepository extends JpaRepository<PreServiceTraineeResult,Long> {

    List<PreServiceTraineeResult> findByTenantIdAndPreServiceCourseId(Long tenantId, Long courseId);
    boolean existsByTenantIdAndPreServiceTraineeIdAndPreServiceCourseIdAndSemester(
            Long tenantId, Long preServiceTraineeId, Long preServiceCourseId, Semester semester);
}
