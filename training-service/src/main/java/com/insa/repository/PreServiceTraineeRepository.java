package com.insa.repository;

import com.insa.entity.PreServiceTrainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreServiceTraineeRepository extends JpaRepository<PreServiceTrainee, Long> {

    List<PreServiceTrainee> findByTenantIdAndBudgetYearId(Long tenantId, Long yearId);
    boolean existsByTenantIdAndTraineeId(Long tenantId, String traineeId);
}
