package com.recruitment_service.repository;

import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReferenceRepository extends JpaRepository<Reference, UUID> {

    List<Reference> findByTenantIdAndApplicant(UUID tenantId, Applicant applicant);
}
