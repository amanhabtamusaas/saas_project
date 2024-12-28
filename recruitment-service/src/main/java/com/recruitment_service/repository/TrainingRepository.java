package com.recruitment_service.repository;

import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {

    List<Training> findByTenantIdAndApplicant(UUID tenantId, Applicant applicant);
}
