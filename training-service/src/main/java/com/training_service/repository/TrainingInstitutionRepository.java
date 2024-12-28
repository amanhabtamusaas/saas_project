package com.training_service.repository;

import com.training_service.model.TrainingInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrainingInstitutionRepository extends JpaRepository<TrainingInstitution, UUID> {
    boolean existsByTenantIdAndInstitutionName(UUID tenantId, String institutionName);
}
