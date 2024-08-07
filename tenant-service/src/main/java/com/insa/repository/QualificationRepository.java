package com.insa.repository;

import com.insa.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification,Long> {
    boolean existsByQualification(String qualification);

    boolean existsByQualificationAndTenantId(String qualification, Long tenantId);
}
