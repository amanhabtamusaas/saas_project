package com.leave_service.service;

import com.leave_service.client.OrganizationServiceClient;
import com.leave_service.dto.TenantDto;
import com.leave_service.dto.request.LeaveSettingRequest;
import com.leave_service.dto.response.LeaveSettingResponse;
import com.leave_service.model.LeaveSetting;
import com.leave_service.exception.ResourceExistsException;
import com.leave_service.exception.ResourceNotFoundException;
import com.leave_service.mapper.LeaveSettingMapper;
import com.leave_service.repository.LeaveSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveSettingService {

    private final LeaveSettingRepository leaveSettingRepository;
    private final LeaveSettingMapper leaveSettingMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public LeaveSettingResponse createLeaveSetting(UUID tenantId, LeaveSettingRequest leaveSettingRequest) {
        // Validate the tenant ID
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        if (leaveSettingRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("LeaveType with tenant " + tenant.getId() + " already exists");
        }

        // Map the request to an entity
        LeaveSetting leaveSetting = leaveSettingMapper.toLeaveSetting(leaveSettingRequest);
        leaveSetting.setTenantId(tenant.getId());

        // Save the leave setting entity
        LeaveSetting savedLeaveSetting = leaveSettingRepository.save(leaveSetting);

        // Map the saved entity back to a response DTO
        return leaveSettingMapper.toLeaveSettingResponseDTO(savedLeaveSetting);
    }

    public List<LeaveSettingResponse> getAllLeaveSettings(UUID tenantId) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Retrieve all leave setting entities
        List<LeaveSetting> leaveSettings = leaveSettingRepository.findAll();

        // Map the entities to response DTOs
        return leaveSettings.stream()
                .map(leaveSettingMapper::toLeaveSettingResponseDTO)
                .collect(Collectors.toList());
    }

    public LeaveSettingResponse getLeaveSettingById(UUID tenantId, UUID id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the leave setting entity by ID
        LeaveSetting leaveSetting = leaveSettingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveSetting not found with ID: " + id));

        // Map the entity to a response DTO
        return leaveSettingMapper.toLeaveSettingResponseDTO(leaveSetting);
    }

    public LeaveSettingResponse updateLeaveSetting(UUID tenantId, UUID id, LeaveSettingRequest leaveSettingRequest) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the leave setting entity by ID
        LeaveSetting leaveSetting = leaveSettingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveSetting not found with ID: " + id));

        // Update the leave setting entity with data from the request
        leaveSettingMapper.updateLeaveSetting(leaveSetting, leaveSettingRequest);

        // Save the updated leave setting entity
        LeaveSetting updatedLeaveSetting = leaveSettingRepository.save(leaveSetting);

        // Map the updated entity back to a response DTO
        return leaveSettingMapper.toLeaveSettingResponseDTO(updatedLeaveSetting);
    }

    public void deleteLeaveSetting(UUID tenantId, UUID id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the leave setting entity by ID
        LeaveSetting leaveSetting = leaveSettingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveSetting not found with ID: " + id));

        // Delete the leave setting entity
        leaveSettingRepository.delete(leaveSetting);
    }
}