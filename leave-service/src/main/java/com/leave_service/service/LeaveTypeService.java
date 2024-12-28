package com.leave_service.service;

import com.leave_service.client.OrganizationServiceClient;
import com.leave_service.dto.request.LeaveTypeRequest;
import com.leave_service.dto.response.LeaveTypeResponse;
import com.leave_service.model.LeaveType;
import com.leave_service.exception.ResourceExistsException;
import com.leave_service.exception.ResourceNotFoundException;
import com.leave_service.mapper.LeaveTypeMapper;
import com.leave_service.repository.LeaveTypeRepository;
import com.leave_service.dto.TenantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeMapper leaveTypeMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public LeaveTypeResponse createLeaveType(UUID tenantId, LeaveTypeRequest leaveTypeRequest) {
        // Validate tenant ID
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        if (leaveTypeRepository.existsByLeaveTypeNameAndTenantId(leaveTypeRequest.getLeaveTypeName(), tenant.getId())) {
            throw new ResourceExistsException("LeaveType with name " + leaveTypeRequest.getLeaveTypeName() + " already exists");
        }
        LeaveType leaveType = leaveTypeMapper.toEntity(leaveTypeRequest, tenantId);
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toResponseDTO(savedLeaveType);
    }

    public List<LeaveTypeResponse> getAllLeaveTypes(UUID tenantId) {
        // Validate tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();
        return leaveTypes.stream()
                .map(leaveTypeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LeaveTypeResponse getLeaveTypeById(UUID tenantId, UUID id) {
        // Validate tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        return leaveTypeMapper.toResponseDTO(leaveType);
    }

    public LeaveTypeResponse updateLeaveType(UUID tenantId, UUID id, LeaveTypeRequest leaveTypeRequest) {
        // Validate tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        leaveTypeMapper.updateLeaveType(leaveType, leaveTypeRequest);
        LeaveType updatedLeaveType = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toResponseDTO(updatedLeaveType);
    }

    public void deleteLeaveType(UUID tenantId, UUID id) {
        // Validate tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        leaveTypeRepository.delete(leaveType);
    }
}