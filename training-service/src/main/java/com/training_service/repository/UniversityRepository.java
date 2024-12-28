package com.training_service.repository;

import com.training_service.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UniversityRepository extends JpaRepository<University, UUID> {

    boolean existsByTenantIdAndUniversityName(UUID tenantId, String universityName);
}
