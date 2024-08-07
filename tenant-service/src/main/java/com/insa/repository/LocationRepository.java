package com.insa.repository;

import com.insa.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    List<Location> findByTenantId(Long tenantId);

    boolean existsByLocationName(String locationName);
    @Query("SELECT d FROM Location d WHERE d.parentLocation IS NULL")
    List<Location> findAllParentLocations();

    boolean existsByLocationNameAndTenantId(String locationName, Long tenantId);
    boolean existsByLocationNameAndParentLocation(String locationName, Location parentLocation);
    boolean existsByLocationNameAndTenantIdAndIdNot(String locationName, Long tenantId, Long id);
    List<Location> findByParentLocation(Location parentLocation);


}
