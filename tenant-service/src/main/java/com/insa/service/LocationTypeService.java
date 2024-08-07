package com.insa.service;

import com.insa.dto.requestDto.LocationTypeRequest;
import com.insa.dto.responseDto.LocationTypeResponse;
import com.insa.entity.LocationType;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.LocationTypeMapper;
import com.insa.repository.LocationTypeRepository;
import com.insa.repository.TenantRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationTypeService {

    private final LocationTypeRepository locationTypeRepository;
    private final LocationTypeMapper locationTypeMapper;
    private final TenantRepository tenantRepository;

    public LocationTypeResponse createLocationType(Long tenantId, @Valid LocationTypeRequest locationTypeRequest) {
        // Validate input data
        if (locationTypeRequest.getLocationTypeName() == null || locationTypeRequest.getLocationTypeName().isEmpty()) {
            throw new IllegalArgumentException("Location type name must be provided");
        }

        if (locationTypeRequest.getDescription() == null || locationTypeRequest.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description must be provided");
        }

        // Check if the tenant exists
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        // Check if the location type already exists for the tenant
        if (locationTypeRepository.existsByLocationTypeNameAndTenantId(locationTypeRequest.getLocationTypeName(), tenant.getId())) {
            throw new ResourceExistsException("LocationType with Name " + locationTypeRequest.getLocationTypeName() + " already exists for tenant ID " + tenantId);
        }

        // Map request to entity
        LocationType locationType = locationTypeMapper.mapToEntity(locationTypeRequest);
        locationType.setTenant(tenant);

        // Persist the entity
        locationType = locationTypeRepository.save(locationType);

        // Map entity to response DTO
        return locationTypeMapper.mapToDto(locationType);
    }

    public List<LocationTypeResponse> getAllLocationTypes(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        List<LocationType> locationTypes = locationTypeRepository.findAll();
        return locationTypes.stream()
                .filter(lt -> lt.getTenant().getId().equals(tenant.getId()))
                .map(locationTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public LocationTypeResponse getLocationTypeById(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        LocationType locationType = locationTypeRepository.findById(id)
                .filter(lt -> lt.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found with id: " + id + " for the specified tenant"));

        return locationTypeMapper.mapToDto(locationType);
    }

    public LocationTypeResponse updateLocationType(Long id, Long tenantId, LocationTypeRequest locationTypeRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        LocationType locationType = locationTypeRepository.findById(id)
                .filter(lt -> lt.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found with id: " + id + " for the specified tenant"));

        // Check if the location type name already exists for the tenant (excluding the current location type)
        if (locationTypeRepository.existsByLocationTypeNameAndTenantIdAndIdNot(locationTypeRequest.getLocationTypeName(), tenantId, id)) {
            throw new ResourceExistsException("LocationType with Name " + locationTypeRequest.getLocationTypeName() + " already exists for tenant ID " + tenantId);
        }

        locationType = locationTypeMapper.updateLocationType(locationType, locationTypeRequest);
        locationType = locationTypeRepository.save(locationType);

        return locationTypeMapper.mapToDto(locationType);
    }

    public void deleteLocationType(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        LocationType locationType = locationTypeRepository.findById(id)
                .filter(lt -> lt.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("LocationType not found with id: " + id + " for the specified tenant"));

        locationTypeRepository.delete(locationType);
    }
}
