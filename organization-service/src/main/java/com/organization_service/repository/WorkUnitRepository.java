package com.organization_service.repository;

import com.organization_service.model.WorkUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkUnitRepository extends JpaRepository<WorkUnit, UUID> {
    boolean existsByWorkUnitNameAndTenantId(String workUnitName, UUID tenantId);
}
