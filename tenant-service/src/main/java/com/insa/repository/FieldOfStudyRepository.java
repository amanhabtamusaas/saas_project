package com.insa.repository;

import com.insa.entity.FieldOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy,Long> {
    boolean existsByFieldOfStudy(String fieldOfStudy);

    boolean existsByFieldOfStudyAndTenantId(String fieldOfStudy, Long tenantId);
}
