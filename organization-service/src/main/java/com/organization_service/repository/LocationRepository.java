package com.organization_service.repository;

import com.organization_service.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    List<Location> findByTenantId(UUID tenantId);

    boolean existsByLocationName(String locationName);
    @Query("SELECT d FROM Location d WHERE d.parentLocation IS NULL")
    List<Location> findAllParentLocations();

    boolean existsByLocationNameAndTenantId(String locationName, UUID tenantId);
    boolean existsByLocationNameAndParentLocation(String locationName, Location parentLocation);
    boolean existsByLocationNameAndTenantIdAndIdNot(String locationName, UUID tenantId, UUID id);
    List<Location> findByParentLocation(Location parentLocation);


}
