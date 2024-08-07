package com.insa.repository;

import com.insa.entity.DepartmentType;
import com.insa.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DepartmentTypeRepository extends JpaRepository<DepartmentType,Long> {
    Optional<DepartmentType> findByTenantId(Long tenantId);

    boolean existsByDepartmentTypeName(String departmentTypeName);

    boolean existsByDepartmentTypeNameAndTenantId(String departmentTypeName, Long tenantId);
}
