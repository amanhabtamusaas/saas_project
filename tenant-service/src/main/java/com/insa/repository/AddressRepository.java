package com.insa.repository;

import com.insa.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByTenantId(Long tenantId);

    List<Address> findByDepartmentId(Long departmentId);

    boolean existsByDepartmentIdAndTenantId(Long departmentId, Long tenantId);
}
