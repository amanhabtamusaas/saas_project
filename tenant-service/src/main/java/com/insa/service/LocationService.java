package com.insa.service;

import com.insa.dto.requestDto.LocationRequest;
import com.insa.dto.responseDto.LocationResponse;
import com.insa.entity.Location;
import com.insa.entity.LocationType;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.LocationMapper;
import com.insa.repository.LocationRepository;
import com.insa.repository.LocationTypeRepository;
import com.insa.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final TenantRepository tenantRepository;
    private final LocationTypeRepository locationTypeRepository;

    @Transactional
    public LocationResponse createLocation(Long tenantId, LocationRequest locationRequest) {
        if (tenantId == null || locationRequest == null) {
            throw new IllegalArgumentException("TenantId and LocationRequest must not be null");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        LocationType locationType = locationTypeRepository.findById(locationRequest.getLocationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Location type not found with id: " + locationRequest.getLocationTypeId()));

        // Check if a location with the same name already exists for the tenant
        if (locationRepository.existsByLocationNameAndTenantId(locationRequest.getLocationName(), tenant.getId())) {
            throw new ResourceExistsException("Location with name " + locationRequest.getLocationName() + " already exists for tenant ID " + tenantId);
        }

        Location location = locationMapper.mapToEntity(locationRequest);
        location.setTenant(tenant);
        location.setLocationType(locationType);

        location = locationRepository.save(location);

        return locationMapper.mapToDto(location);
    }

    public List<LocationResponse> getAllLocations(Long tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        List<Location> locations = locationRepository.findAll();

        return locations.stream()
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .map(locationMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public LocationResponse getLocationById(Long id, Long tenantId) {
        validateIds(id, tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location location = locationRepository.findById(id)
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id + " for the specified tenant"));

        return locationMapper.mapToDto(location);
    }

    public LocationResponse updateLocation(Long id, Long tenantId, LocationRequest locationRequest) {
        validateIdsAndRequest(id, tenantId, locationRequest);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location location = locationRepository.findById(id)
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id + " for the specified tenant"));

        // Check if a location with the same name already exists for the tenant (excluding the current location)
        if (locationRepository.existsByLocationNameAndTenantIdAndIdNot(locationRequest.getLocationName(), tenantId, id)) {
            throw new ResourceExistsException("Location with name " + locationRequest.getLocationName() + " already exists for tenant ID " + tenantId);
        }

        location = locationMapper.updateLocation(location, locationRequest);

        location = locationRepository.save(location);

        return locationMapper.mapToDto(location);
    }

    public void deleteLocation(Long id, Long tenantId) {
        validateIds(id, tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location location = locationRepository.findById(id)
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id + " for the specified tenant"));

        locationRepository.delete(location);
    }

    private void validateIds(Long id, Long tenantId) {
        if (id == null || tenantId == null) {
            throw new IllegalArgumentException("LocationId and TenantId must not be null");
        }
    }

    private void validateIdsAndRequest(Long id, Long tenantId, LocationRequest locationRequest) {
        if (id == null || tenantId == null || locationRequest == null) {
            throw new IllegalArgumentException("LocationId, TenantId, and LocationRequest must not be null");
        }
    }

    @Transactional
    private void deleteChildLocations(Location parentLocation) {
        List<Location> childDepartments = locationRepository.findByParentLocation(parentLocation);
        for (Location childDepartment : childDepartments) {
            deleteChildLocations(childDepartment);
            locationRepository.delete(childDepartment);
        }
    }

    public LocationResponse addSubLocation(Long parentId, Long tenantId, LocationRequest subLocationRequest) {
        validateIds(parentId, tenantId);

        Location parentLocation = locationRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent location not found with id: " + parentId));

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        // Check if a sub-location with the same name already exists under the parent location
        if (locationRepository.existsByLocationNameAndParentLocation(subLocationRequest.getLocationName(), parentLocation)) {
            throw new ResourceExistsException("Sub-location with name " + subLocationRequest.getLocationName() + " already exists under parent location with id: " + parentId);
        }

        // Check if a location with the same name already exists for the tenant
        if (locationRepository.existsByLocationNameAndTenantId(subLocationRequest.getLocationName(), tenantId)) {
            throw new ResourceExistsException("Location with name " + subLocationRequest.getLocationName() + " already exists for tenant ID " + tenantId);
        }

        Location subLocation = locationMapper.mapToEntity(subLocationRequest);
        subLocation.setTenant(tenant);
        subLocation.setParentLocation(parentLocation);

        subLocation = locationRepository.save(subLocation);

        return locationMapper.mapToDto(subLocation);
    }

    public void removeSubLocation(Long parentId, Long tenantId, Long subId) {
        if (parentId == null || tenantId == null || subId == null) {
            throw new IllegalArgumentException("ParentId, TenantId, and SubId must not be null");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location parentLocation = locationRepository.findById(parentId)
                .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Parent location not found with id: " + parentId + " for the specified tenant"));

        Location subLocation = locationRepository.findById(subId)
                .filter(dept -> dept.getTenant().getId().equals(tenant.getId()))
                .filter(dept -> dept.getParentLocation() != null && dept.getParentLocation().getId().equals(parentId))
                .orElseThrow(() -> new ResourceNotFoundException("Sub-location not found with id: " + subId + " for the specified parent location"));

        locationRepository.delete(subLocation);
    }

    public Optional<Location> findById(Long parentId) {
        return locationRepository.findById(parentId);
    }

    public List<Location> getAllChildLocations(Long parentId,Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location department = locationRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Location is not found"));
        return department.getAllChildLocations();
    }

    public List<LocationResponse> getAllParentLocations(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        List<Location> parentLocations = locationRepository.findAllParentLocations();
        return parentLocations.stream()
                .map(locationMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
