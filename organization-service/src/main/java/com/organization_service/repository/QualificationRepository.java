package com.organization_service.repository;

import com.organization_service.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, UUID> {
    boolean existsByQualification(String qualification);

    boolean existsByQualificationAndTenantId(String qualification, UUID tenantId);
}
