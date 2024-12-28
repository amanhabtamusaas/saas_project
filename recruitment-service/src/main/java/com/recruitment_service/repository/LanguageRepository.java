package com.recruitment_service.repository;

import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LanguageRepository extends JpaRepository<Language, UUID> {

    List<Language> findByTenantIdAndApplicant(UUID tenantId, Applicant applicant);
}
