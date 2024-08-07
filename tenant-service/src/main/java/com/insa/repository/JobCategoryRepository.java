package com.insa.repository;

import com.insa.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory,Long> {
    boolean existsByJobCategoryName(String jobCategoryName);

    boolean existsByJobCategoryNameAndTenantId(String jobCategoryName, Long tenantId);
}
