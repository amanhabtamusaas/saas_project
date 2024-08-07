package com.insa.repository;

import com.insa.entity.TrainingInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingInstitutionRepository extends JpaRepository<TrainingInstitution, Long> {
    boolean existsByTenantIdAndInstitutionName(Long tenantId, String institutionName);
}
