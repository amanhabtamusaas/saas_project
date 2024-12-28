package com.organization_service.repository;

import com.organization_service.model.StaffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface StaffPlanRepository extends JpaRepository<StaffPlan, UUID> {
    List<StaffPlan> findByDepartmentId(UUID departmentId);

    boolean existsByTenantId(UUID tenantId);
}