package com.insa.leave_service.repository;

import com.insa.leave_service.entity.LeaveSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveScheduleRepository extends JpaRepository<LeaveSchedule,Long> {


    boolean existsByTenantId(Long tenantId);
}
