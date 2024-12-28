package com.recruitment_service.repository;

import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {

    List<Applicant> findByTenantIdAndRecruitment(UUID tenantId, Recruitment recruitment);
}
