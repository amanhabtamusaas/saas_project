package com.insa.leave_service.repository;

import com.insa.leave_service.entity.LeaveSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveSettingRepository extends JpaRepository<LeaveSetting,Long> {
    LeaveSetting findByTenantId(Long tenantId);


    boolean existsByTenantId(Long id);
}
