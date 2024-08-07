package com.insa.leave_service.repository;

import com.insa.leave_service.entity.BudgetYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetYearRepository extends JpaRepository<BudgetYear, Long> {

    // Use a custom query to find the current budget year
    @Query("SELECT b FROM BudgetYear b WHERE b.isActive = true")
    BudgetYear findCurrentBudgetYear();
    boolean existsByBudgetYearAndTenantId(String budgetYear,Long tenantId);



}