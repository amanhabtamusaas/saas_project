package com.insa.leave_service.repository;

import com.insa.leave_service.entity.LeaveBalanceHistory;
import com.insa.leave_service.entity.BudgetYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalanceHistoryRepository extends JpaRepository<LeaveBalanceHistory, Long> {
    List<LeaveBalanceHistory> findByEmployeeIdAndTenantIdOrderByCalculationDateDesc(Long employeeId, Long tenantId);

}