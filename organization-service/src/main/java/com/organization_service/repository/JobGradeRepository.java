package com.organization_service.repository;

import com.organization_service.model.JobGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobGradeRepository extends JpaRepository<JobGrade, UUID> {
    boolean existsByJobGradeName(String jobGradeName);

    boolean existsByJobGradeNameAndTenantId(String jobGradeName, UUID tenantId);
}
