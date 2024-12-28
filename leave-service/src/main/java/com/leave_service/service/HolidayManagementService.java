package com.leave_service.service;

import com.leave_service.client.OrganizationServiceClient;
import com.leave_service.dto.TenantDto;
import com.leave_service.dto.request.HolidayManagementRequest;
import com.leave_service.dto.response.HolidayManagementResponse;
import com.leave_service.model.HolidayManagement;
import com.leave_service.exception.ResourceNotFoundException;
import com.leave_service.mapper.HolidayManagementMapper;
import com.leave_service.repository.HolidayManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayManagementService {

    private final HolidayManagementRepository holidayManagementRepository;
    private final HolidayManagementMapper holidayManagementMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public HolidayManagementResponse createHolidayManagement(UUID tenantId,
                                                             HolidayManagementRequest holidayManagementRequest) {

        // Validate the tenant ID
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Map the request to an entity
        HolidayManagement holidayManagement = holidayManagementMapper.mapToEntity(holidayManagementRequest);
        holidayManagement.setTenantId(tenant.getId());

        // Save the holiday management entity
        HolidayManagement savedHolidayManagement = holidayManagementRepository.save(holidayManagement);

        // Map the saved entity back to a response DTO
        return holidayManagementMapper.mapToDto(savedHolidayManagement);
    }

    public List<HolidayManagementResponse> getAllHolidayManagements(UUID tenantId) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Retrieve all holiday management entities
        List<HolidayManagement> holidayManagements = holidayManagementRepository.findAll();

        // Map the entities to response DTOs
        return holidayManagements.stream()
                .map(holidayManagementMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HolidayManagementResponse getHolidayManagementById(UUID tenantId, UUID id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Map the entity to a response DTO
        return holidayManagementMapper.mapToDto(holidayManagement);
    }

    public HolidayManagementResponse updateHolidayManagement(UUID tenantId, UUID id,
                                                             HolidayManagementRequest holidayManagementRequest) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Update the holiday management entity with data from the request
        holidayManagementMapper.updateHolidayManagement(holidayManagement, holidayManagementRequest);

        // Save the updated holiday management entity
        HolidayManagement updatedHolidayManagement = holidayManagementRepository.save(holidayManagement);

        // Map the updated entity back to a response DTO
        return holidayManagementMapper.mapToDto(updatedHolidayManagement);
    }

    public void deleteHolidayManagement(UUID tenantId, UUID id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Delete the holiday management entity
        holidayManagementRepository.delete(holidayManagement);
    }
}