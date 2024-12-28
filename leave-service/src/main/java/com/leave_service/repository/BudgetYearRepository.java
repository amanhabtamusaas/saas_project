package com.leave_service.repository;

import com.leave_service.model.BudgetYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetYearRepository extends JpaRepository<BudgetYear, UUID> {

    // Use a custom query to find the current budget year
    @Query("SELECT b FROM BudgetYear b WHERE b.isActive = true")
    BudgetYear findCurrentBudgetYear();
    boolean existsByBudgetYearAndTenantId(String budgetYear,UUID tenantId);



}