package com.organization_service.service;

import com.organization_service.dto.requestDto.LocationRequest;
import com.organization_service.dto.responseDto.LocationResponse;
import com.organization_service.model.Location;
import com.organization_service.model.LocationType;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.LocationMapper;
import com.organization_service.repository.LocationRepository;
import com.organization_service.repository.LocationTypeRepository;
import com.organization_service.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final TenantRepository tenantRepository;
    private final LocationTypeRepository locationTypeRepository;

    @Transactional
    public LocationResponse createLocation(UUID tenantId, LocationRequest locationRequest) {
        if (tenantId == null || locationRequest == null) {
            throw new IllegalArgumentException("TenantId and LocationRequest must not be null");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        LocationType locationType = locationTypeRepository.findById(locationRequest.getLocationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Location type not found with id: " +
                        locationRequest.getLocationTypeId()));

        // Check if a location with the same name already exists for the tenant
        if (locationRepository.existsByLocationNameAndTenantId(locationRequest.getLocationName(), tenant.getId())) {
            throw new ResourceExistsException("Location with name " + locationRequest.getLocationName() +
                    " already exists for tenant ID " + tenantId);
        }

        Location location = locationMapper.mapToEntity(locationRequest);
        location.setTenant(tenant);
        location.setLocationType(locationType);

        location = locationRepository.save(location);

        return locationMapper.mapToDto(location);
    }

    public List<LocationResponse> getAllLocations(UUID tenantId) {
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

    public LocationResponse getLocationById(UUID id, UUID tenantId) {
        validateIds(id, tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location location = locationRepository.findById(id)
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id +
                        " for the specified tenant"));

        return locationMapper.mapToDto(location);
    }

    public LocationResponse updateLocation(UUID id, UUID tenantId,
                                           LocationRequest locationRequest) {
        validateIdsAndRequest(id, tenantId, locationRequest);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location location = locationRepository.findById(id)
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id +
                        " for the specified tenant"));

        // Check if a location with the same name already exists for the tenant (excluding the current location)
        if (locationRepository.existsByLocationNameAndTenantIdAndIdNot(
                locationRequest.getLocationName(), tenantId, id)) {
            throw new ResourceExistsException("Location with name " + locationRequest.getLocationName() +
                    " already exists for tenant ID " + tenantId);
        }

        location = locationMapper.updateLocation(location, locationRequest);
        location = locationRepository.save(location);

        return locationMapper.mapToDto(location);
    }

    public void deleteLocation(UUID id, UUID tenantId) {
        validateIds(id, tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location location = locationRepository.findById(id)
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id +
                        " for the specified tenant"));

        locationRepository.delete(location);
    }

    private void validateIds(UUID id, UUID tenantId) {
        if (id == null || tenantId == null) {
            throw new IllegalArgumentException("LocationId and TenantId must not be null");
        }
    }

    private void validateIdsAndRequest(UUID id, UUID tenantId, LocationRequest locationRequest) {
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

    public LocationResponse addSubLocation(UUID parentId, UUID tenantId,
                                           LocationRequest subLocationRequest) {
        validateIds(parentId, tenantId);

        Location parentLocation = locationRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent location not found with id: " + parentId));

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        // Check if a sub-location with the same name already exists under the parent location
        if (locationRepository.existsByLocationNameAndParentLocation(
                subLocationRequest.getLocationName(), parentLocation)) {
            throw new ResourceExistsException("Sub-location with name " + subLocationRequest.getLocationName() +
                    " already exists under parent location with id: " + parentId);
        }

        // Check if a location with the same name already exists for the tenant
        if (locationRepository.existsByLocationNameAndTenantId(subLocationRequest.getLocationName(), tenantId)) {
            throw new ResourceExistsException("Location with name " + subLocationRequest.getLocationName() +
                    " already exists for tenant ID " + tenantId);
        }

        Location subLocation = locationMapper.mapToEntity(subLocationRequest);
        subLocation.setTenant(tenant);
        subLocation.setParentLocation(parentLocation);

        subLocation = locationRepository.save(subLocation);

        return locationMapper.mapToDto(subLocation);
    }

    public void removeSubLocation(UUID parentId, UUID tenantId, UUID subId) {
        if (parentId == null || tenantId == null || subId == null) {
            throw new IllegalArgumentException("ParentId, TenantId, and SubId must not be null");
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location parentLocation = locationRepository.findById(parentId)
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Parent location not found with id: " + parentId +
                        " for the specified tenant"));

        Location subLocation = locationRepository.findById(subId)
                .filter(loc -> loc.getTenant().getId().equals(tenant.getId()))
                .filter(loc -> loc.getParentLocation() != null &&
                        loc.getParentLocation().getId().equals(parentId))
                .orElseThrow(() -> new ResourceNotFoundException("Sub-location not found with id: " + subId +
                        " for the specified parent location"));

        locationRepository.delete(subLocation);
    }

    public Optional<Location> findById(UUID parentId) {
        return locationRepository.findById(parentId);
    }

    public List<Location> getAllChildLocations(UUID parentId, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Location department = locationRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Location is not found"));
        return department.getAllChildLocations();
    }

    public List<LocationResponse> getAllParentLocations(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        List<Location> parentLocations = locationRepository.findAllParentLocations();
        return parentLocations.stream()
                .map(locationMapper::mapToDto)
                .collect(Collectors.toList());
    }
}