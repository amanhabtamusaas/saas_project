package com.insa.leave_service.repository;

import com.insa.leave_service.entity.LeaveApproveDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveApproveDepartmentRepository extends JpaRepository<LeaveApproveDepartment,Long> {

    boolean existsByTenantId(Long tenantId);
}
