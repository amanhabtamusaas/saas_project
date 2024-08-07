package com.insa.repository;

import com.insa.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, Long> {
    boolean existsByLocationTypeNameAndTenantId(String locationTypeName, Long tenantId);

    boolean existsByLocationTypeNameAndTenantIdAndIdNot(String locationTypeName, Long tenantId, Long id);
}
