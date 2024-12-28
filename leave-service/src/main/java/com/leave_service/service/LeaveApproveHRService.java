package com.leave_service.service;

import com.leave_service.client.OrganizationServiceClient;
import com.leave_service.dto.TenantDto;
import com.leave_service.dto.request.LeaveApproveHRRequest;
import com.leave_service.dto.response.LeaveApproveHRResponse;
import com.leave_service.model.LeaveApproveHR;
import com.leave_service.exception.ResourceExistsException;
import com.leave_service.exception.ResourceNotFoundException;
import com.leave_service.mapper.LeaveApproveHRMapper;
import com.leave_service.repository.LeaveApproveHRRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveApproveHRService {

    private final LeaveApproveHRRepository leaveApproveHRRepository;
    private final LeaveApproveHRMapper leaveApproveHRMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public LeaveApproveHRResponse createLeaveApproveHR(UUID tenantId,
                                                       LeaveApproveHRRequest leaveApproveHRRequest) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (leaveApproveHRRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("Leave approved by this tenant " +
                    tenant.getId() + " already exists");
        }

        LeaveApproveHR leaveApproveHR = leaveApproveHRMapper.mapToEntity(leaveApproveHRRequest);
        leaveApproveHR.setTenantId(tenant.getId());

        LeaveApproveHR savedLeaveApproveHR = leaveApproveHRRepository.save(leaveApproveHR);
        return leaveApproveHRMapper.mapToDto(savedLeaveApproveHR);
    }

    public List<LeaveApproveHRResponse> getAllLeaveApproveHRs(UUID tenantId) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        List<LeaveApproveHR> leaveApproveHRs = leaveApproveHRRepository.findAll();

        return leaveApproveHRs.stream()
                .map(leaveApproveHRMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public LeaveApproveHRResponse getLeaveApproveHRById(UUID tenantId, UUID id) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveApproveHR leaveApproveHR = leaveApproveHRRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveHR not found with ID: " + id));
        return leaveApproveHRMapper.mapToDto(leaveApproveHR);
    }

    public LeaveApproveHRResponse updateLeaveApproveHR(UUID tenantId, UUID id,
                                                       LeaveApproveHRRequest leaveApproveHRRequest) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveApproveHR leaveApproveHR = leaveApproveHRRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveHR not found with ID: " + id));

        leaveApproveHRMapper.updateLeaveApproveHR(leaveApproveHR, leaveApproveHRRequest);
        LeaveApproveHR updatedLeaveApproveHR = leaveApproveHRRepository.save(leaveApproveHR);
        return leaveApproveHRMapper.mapToDto(updatedLeaveApproveHR);
    }

    public void deleteLeaveApproveHR(UUID tenantId, UUID id) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveApproveHR leaveApproveHR = leaveApproveHRRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveHR not found with ID: " + id));

        leaveApproveHRRepository.delete(leaveApproveHR);
    }
}