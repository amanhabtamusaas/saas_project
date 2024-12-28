package com.organization_service.repository;

import com.organization_service.model.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, UUID> {
    boolean existsByJobCategoryName(String jobCategoryName);

    boolean existsByJobCategoryNameAndTenantId(String jobCategoryName, UUID tenantId);
}
