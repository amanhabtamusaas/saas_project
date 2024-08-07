package com.insa.leave_service.repository;

import com.insa.leave_service.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType,Long> {

    boolean existsByLeaveTypeNameAndTenantId(String budgetYear,Long tenantId);
}
