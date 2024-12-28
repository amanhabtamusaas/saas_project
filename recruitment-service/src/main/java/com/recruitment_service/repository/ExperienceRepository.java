package com.recruitment_service.repository;

import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExperienceRepository extends JpaRepository<Experience, UUID> {

    List<Experience> findByTenantIdAndApplicant(UUID tenantId, Applicant applicant);
}
