package com.insa.repository;

import com.insa.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {

    boolean existsByTenantIdAndUniversityName(Long tenantId, String universityName);
}
