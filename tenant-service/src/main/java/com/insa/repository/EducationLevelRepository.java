package com.insa.repository;

import com.insa.entity.EducationLevel;
import com.insa.entity.JobRegistration;
import com.insa.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long> {
    Optional<EducationLevel> findByIdAndTenant(Long id, Tenant tenant);

    List<EducationLevel> findByTenant(Tenant tenant);

    boolean existsByEducationLevelName(String educationLevelName);

    boolean existsByEducationLevelNameAndTenantId(String educationLevelName, Long tenantId);
}
