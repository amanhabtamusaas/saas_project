package com.leave_service.repository;

import com.leave_service.model.LeaveBalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveBalanceHistoryRepository extends JpaRepository<LeaveBalanceHistory, UUID> {
    List<LeaveBalanceHistory> findByEmployeeIdAndTenantIdOrderByCalculationDateDesc(UUID employeeId, UUID tenantId);

}