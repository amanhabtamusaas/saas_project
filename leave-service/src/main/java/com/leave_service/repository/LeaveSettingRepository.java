package com.leave_service.repository;

import com.leave_service.model.LeaveSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaveSettingRepository extends JpaRepository<LeaveSetting, UUID> {
    LeaveSetting findByTenantId(UUID tenantId);


    boolean existsByTenantId(UUID id);
}
