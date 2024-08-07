package com.insa.leave_service.service;

import com.insa.leave_service.dto.TenantDto;
import com.insa.leave_service.dto.request.LeaveSettingRequest;

import com.insa.leave_service.dto.response.LeaveSettingResponse;

import com.insa.leave_service.entity.LeaveSetting;
import com.insa.leave_service.exception.ResourceExistsException;
import com.insa.leave_service.exception.ResourceNotFoundException;
import com.insa.leave_service.mapper.LeaveSettingMapper;
import com.insa.leave_service.repository.LeaveSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveSettingService {

    private final LeaveSettingRepository leaveSettingRepository;
    private final LeaveSettingMapper leaveSettingMapper;
    private final TenantServiceClient tenantServiceClient;

    public LeaveSettingResponse createLeaveSetting(Long tenantId, LeaveSettingRequest leaveSettingRequest) {
        // Validate the tenant ID
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        if (leaveSettingRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("LeaveType with tenant  " + tenant.getId() + " already exists");
        }

        // Map the request to an entity
        LeaveSetting leaveSetting = leaveSettingMapper.toLeaveSetting(leaveSettingRequest);
        leaveSetting.setTenantId(tenant.getId());

        // Save the leave setting entity
        LeaveSetting savedLeaveSetting = leaveSettingRepository.save(leaveSetting);

        // Map the saved entity back to a response DTO
        return leaveSettingMapper.toLeaveSettingResponseDTO(savedLeaveSetting);
    }

    public List<LeaveSettingResponse> getAllLeaveSettings(Long tenantId) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Retrieve all leave setting entities
        List<LeaveSetting> leaveSettings = leaveSettingRepository.findAll();

        // Map the entities to response DTOs
        return leaveSettings.stream()
                .map(leaveSettingMapper::toLeaveSettingResponseDTO)
                .collect(Collectors.toList());
    }

    public LeaveSettingResponse getLeaveSettingById(Long tenantId, Long id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the leave setting entity by ID
        LeaveSetting leaveSetting = leaveSettingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveSetting not found with ID: " + id));

        // Map the entity to a response DTO
        return leaveSettingMapper.toLeaveSettingResponseDTO(leaveSetting);
    }

    public LeaveSettingResponse updateLeaveSetting(Long tenantId, Long id, LeaveSettingRequest leaveSettingRequest) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

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

    public void deleteLeaveSetting(Long tenantId, Long id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the leave setting entity by ID
        LeaveSetting leaveSetting = leaveSettingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveSetting not found with ID: " + id));

        // Delete the leave setting entity
        leaveSettingRepository.delete(leaveSetting);
    }
}
