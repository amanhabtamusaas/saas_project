package com.insa.repository;

import com.insa.entity.JobGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobGradeRepository extends JpaRepository<JobGrade,Long> {
    boolean existsByJobGradeName(String jobGradeName);

    boolean existsByJobGradeNameAndTenantId(String jobGradeName, Long tenantId);
}
