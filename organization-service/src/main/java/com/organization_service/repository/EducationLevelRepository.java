package com.organization_service.repository;

import com.organization_service.model.EducationLevel;
import com.organization_service.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevel, UUID> {
    Optional<EducationLevel> findByIdAndTenant(UUID id, Tenant tenant);

    List<EducationLevel> findByTenant(Tenant tenant);

    boolean existsByEducationLevelName(String educationLevelName);

    boolean existsByEducationLevelNameAndTenantId(String educationLevelName, UUID tenantId);
}
