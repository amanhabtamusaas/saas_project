package com.organization_service.repository;

import com.organization_service.model.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, UUID> {
    boolean existsByLocationTypeNameAndTenantId(String locationTypeName, UUID tenantId);

    boolean existsByLocationTypeNameAndTenantIdAndIdNot(String locationTypeName, UUID tenantId, UUID id);
}
