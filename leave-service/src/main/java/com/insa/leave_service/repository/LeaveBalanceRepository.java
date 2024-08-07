package com.insa.leave_service.repository;

import com.insa.leave_service.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    Optional<LeaveBalance> findByEmployeeIdAndTenantId(Long employeeId, Long tenantId);
    // You can define custom query methods here if needed

}