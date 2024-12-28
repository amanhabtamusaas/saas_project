package com.leave_service.repository;

import com.leave_service.model.LeaveApproveDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveApproveDepartmentRepository extends JpaRepository<LeaveApproveDepartment, UUID> {

    boolean existsByTenantId(UUID tenantId);
}
