package com.training_service.repository;

import com.training_service.model.AnnualTrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnnualTrainingPlanRepository extends JpaRepository<AnnualTrainingPlan, UUID> {

    boolean existsByTenantIdAndDepartmentIdAndBudgetYearIdAndTrainingCourseId(
            UUID tenantId, UUID departmentId, UUID budgetYearId, UUID trainingCourseId);
}
