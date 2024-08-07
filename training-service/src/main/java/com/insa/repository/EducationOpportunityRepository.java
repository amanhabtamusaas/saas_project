package com.insa.repository;

import com.insa.entity.EducationOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationOpportunityRepository extends JpaRepository<EducationOpportunity, Long> {

    List<EducationOpportunity> findByTenantIdAndEmployeeId(Long tenantId, Long employeeId);
    boolean existsByTenantIdAndBudgetYearIdAndEmployeeId(Long tenantId, Long budgetYearId, Long employeeId);
}
