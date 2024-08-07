package com.insa.mapper;

import com.insa.dto.requestDto.DepartmentRequest;
import com.insa.dto.requestDto.LocationRequest;
import com.insa.dto.responseDto.DepartmentResponse;
import com.insa.dto.responseDto.LocationResponse;
import com.insa.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {
    @Autowired
    private TenantMapper tenantMapper;
    public Location mapToEntity(LocationRequest locationRequest) {
        Location location = new Location();
        location.setLocationName(locationRequest.getLocationName());

        LocationType locationType = new LocationType();
        locationType.setId(locationRequest.getLocationTypeId());
        location.setLocationType(locationType);
        Tenant tenant = new Tenant();
        tenant.setId(locationRequest.getTenantId());
        location.setTenant(tenant);

        return location;
    }

    public LocationResponse mapToDto(Location location) {
        LocationResponse response = new LocationResponse ();
        response.setId(location.getId());
        response.setLocationName (location.getLocationName ());
       response.setLocationTypeId(location.getLocationType().getId());
        response.setTenantId(location.getTenant().getId());
        if (location.getParentLocation() != null) {
            response.setParentLocationId(location.getParentLocation().getId());
        }

        if (location.getSubLocations() != null) {
            List<Long> subLocationsId = location.getSubLocations()
                    .stream()
                    .map(Location::getId)
                    .collect(Collectors.toList());
            response.setSubLocationIds(subLocationsId);
        }


        return response;
    }

    public Location updateLocation(Location location, LocationRequest locationRequest) {
        if (locationRequest.getLocationName() != null) {
            location.setLocationName(locationRequest.getLocationName());
        }
        if (locationRequest.getLocationTypeId() != null) {
            LocationType locationType = new LocationType();
            locationType.setId(locationRequest.getLocationTypeId());
            location.setLocationType(locationType); // Update location's locationType field
        }

        return location;
    }

}
