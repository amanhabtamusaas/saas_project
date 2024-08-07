package com.insa.repository;
import com.insa.entity.Tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant,Long> {
    boolean existsByTenantName(String tenantName);
}
