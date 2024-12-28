package com.training_service.repository;

import com.training_service.model.EducationOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EducationOpportunityRepository extends JpaRepository<EducationOpportunity, UUID> {

    List<EducationOpportunity> findByTenantIdAndEmployeeId(UUID tenantId, UUID employeeId);
    boolean existsByTenantIdAndBudgetYearIdAndEmployeeId(UUID tenantId, UUID budgetYearId, UUID employeeId);
}
