package com.insa.leave_service.repository;

import com.insa.leave_service.entity.LeaveApproveHR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveApproveHRRepository extends JpaRepository<LeaveApproveHR,Long> {

    boolean existsByTenantId(Long tenantId);
}
