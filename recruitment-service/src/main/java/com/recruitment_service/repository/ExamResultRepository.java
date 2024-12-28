package com.recruitment_service.repository;

import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.ExamResult;
import com.recruitment_service.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExamResultRepository extends JpaRepository<ExamResult, UUID> {

    ExamResult findByApplicantId(UUID applicantId);
    boolean existsByApplicant(Applicant applicant);
    List<ExamResult> findByTenantIdAndRecruitment(UUID tenantId, Recruitment recruitment);
}
