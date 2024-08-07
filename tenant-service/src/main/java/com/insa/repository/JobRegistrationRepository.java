package com.insa.repository;

import com.insa.entity.Address;
import com.insa.entity.Department;
import com.insa.entity.JobRegistration;
import com.insa.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRegistrationRepository extends JpaRepository<JobRegistration, Long> {
    Optional<JobRegistration> findByTenantId(Long tenantId);
    List<JobRegistration> findByTenantAndDepartment(Tenant tenant, Department department);

    boolean existsByJobTitle(String jobTitle);

    boolean existsByJobCode(String jobCode);

    boolean existsByJobTitleAndTenantId(String jobTitle, Long tenantId);
}
