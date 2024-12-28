package com.leave_service.repository;

import com.leave_service.model.LeaveSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveScheduleRepository extends JpaRepository<LeaveSchedule, UUID> {


    boolean existsByTenantId(UUID tenantId);
}
