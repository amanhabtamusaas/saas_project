package com.insa.leave_service.repository;

import com.insa.leave_service.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {
    List<LeaveRequest> findByEmployeeId(Long employeeId);


    boolean existsByTenantId(Long tenantId);
}
