package com.insa.repository;

import com.insa.entity.AnnualTrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualTrainingPlanRepository extends JpaRepository<AnnualTrainingPlan, Long> {

    boolean existsByTenantIdAndDepartmentIdAndBudgetYearIdAndTrainingCourseId(
            Long tenantId, Long departmentId, Long budgetYearId, Long trainingCourseId);
}
