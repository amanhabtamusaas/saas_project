package com.insa.leave_service.service;

import com.insa.leave_service.dto.TenantDto;
import com.insa.leave_service.dto.request.LeaveApproveHRRequest;

import com.insa.leave_service.dto.response.LeaveApproveHRResponse;

import com.insa.leave_service.entity.LeaveApproveHR;
import com.insa.leave_service.exception.ResourceExistsException;
import com.insa.leave_service.exception.ResourceNotFoundException;
import com.insa.leave_service.mapper.LeaveApproveHRMapper;
import com.insa.leave_service.repository.LeaveApproveHRRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveApproveHRService {

    private final LeaveApproveHRRepository leaveApproveHRRepository;
    private final LeaveApproveHRMapper leaveApproveHRMapper;
    private final TenantServiceClient tenantServiceClient;

    public LeaveApproveHRResponse createLeaveApproveHR(Long tenantId, LeaveApproveHRRequest leaveApproveHRRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (leaveApproveHRRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("leave approved by this tenant  " + tenant.getId() +  " already exists");
        }


        LeaveApproveHR leaveApproveHR = leaveApproveHRMapper.mapToEntity(leaveApproveHRRequest);
        leaveApproveHR.setTenantId(tenant.getId());

        LeaveApproveHR savedLeaveApproveHR = leaveApproveHRRepository.save(leaveApproveHR);

        return leaveApproveHRMapper.mapToDto(savedLeaveApproveHR);
    }

    public List<LeaveApproveHRResponse> getAllLeaveApproveHRs(Long tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        List<LeaveApproveHR> leaveApproveHRs = leaveApproveHRRepository.findAll();

        return leaveApproveHRs.stream()
                .map(leaveApproveHRMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public LeaveApproveHRResponse getLeaveApproveHRById(Long tenantId, Long id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        LeaveApproveHR leaveApproveHR = leaveApproveHRRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveHR not found with ID: " + id));

        return leaveApproveHRMapper.mapToDto(leaveApproveHR);
    }

    public LeaveApproveHRResponse updateLeaveApproveHR(Long tenantId, Long id, LeaveApproveHRRequest leaveApproveHRRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        LeaveApproveHR leaveApproveHR = leaveApproveHRRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveHR not found with ID: " + id));

        leaveApproveHRMapper.updateLeaveApproveHR(leaveApproveHR, leaveApproveHRRequest);

        LeaveApproveHR updatedLeaveApproveHR = leaveApproveHRRepository.save(leaveApproveHR);

        return leaveApproveHRMapper.mapToDto(updatedLeaveApproveHR);
    }

    public void deleteLeaveApproveHR(Long tenantId, Long id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        LeaveApproveHR leaveApproveHR = leaveApproveHRRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveHR not found with ID: " + id));

        leaveApproveHRRepository.delete(leaveApproveHR);
    }
}
