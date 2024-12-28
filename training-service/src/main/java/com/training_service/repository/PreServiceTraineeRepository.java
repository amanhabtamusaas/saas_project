package com.training_service.repository;

import com.training_service.model.PreServiceTrainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PreServiceTraineeRepository extends JpaRepository<PreServiceTrainee, UUID> {

    List<PreServiceTrainee> findByTenantIdAndBudgetYearId(UUID tenantId, UUID yearId);
    boolean existsByTenantIdAndTraineeId(UUID tenantId, String traineeId);
}
