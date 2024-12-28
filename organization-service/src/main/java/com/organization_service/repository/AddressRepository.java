package com.organization_service.repository;

import com.organization_service.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByTenantId(UUID tenantId);

    List<Address> findByDepartmentId(UUID departmentId);

    boolean existsByDepartmentIdAndTenantId(UUID departmentId, UUID tenantId);
}
