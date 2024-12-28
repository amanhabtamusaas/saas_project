package com.leave_service.repository;

import com.leave_service.model.LeaveApproveHR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveApproveHRRepository extends JpaRepository<LeaveApproveHR, UUID> {

    boolean existsByTenantId(UUID tenantId);
}
