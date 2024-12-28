package com.organization_service.repository;

import com.organization_service.model.DepartmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentHistoryRepository extends JpaRepository<DepartmentHistory, UUID> {
    Optional<DepartmentHistory> findByTenantId(UUID tenantId);

}
