package com.insa.leave_service.service;

import com.insa.leave_service.dto.request.LeaveTypeRequest;

import com.insa.leave_service.dto.response.LeaveTypeResponse;

import com.insa.leave_service.entity.LeaveType;
import com.insa.leave_service.exception.ResourceExistsException;
import com.insa.leave_service.exception.ResourceNotFoundException;
import com.insa.leave_service.mapper.LeaveTypeMapper;
import com.insa.leave_service.repository.LeaveTypeRepository;

import com.insa.leave_service.dto.TenantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveTypeMapper leaveTypeMapper;
    private final TenantServiceClient tenantServiceClient;

    public LeaveTypeResponse createLeaveType(Long tenantId, LeaveTypeRequest leaveTypeRequest) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        if (leaveTypeRepository.existsByLeaveTypeNameAndTenantId(leaveTypeRequest.getLeaveTypeName(),tenant.getId())) {
            throw new ResourceExistsException("LeaveType with name " + leaveTypeRequest.getLeaveTypeName() + " already exists");
        }
        LeaveType leaveType = leaveTypeMapper.toEntity(leaveTypeRequest, tenantId);
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toResponseDTO(savedLeaveType);
    }

    public List<LeaveTypeResponse> getAllLeaveTypes(Long tenantId) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();
        return leaveTypes.stream()
                .map(leaveTypeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LeaveTypeResponse getLeaveTypeById(Long tenantId, Long id) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        return leaveTypeMapper.toResponseDTO(leaveType);
    }

    public LeaveTypeResponse updateLeaveType(Long tenantId, Long id, LeaveTypeRequest leaveTypeRequest) {
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        leaveTypeMapper.updateLeaveType(leaveType, leaveTypeRequest);
        LeaveType updatedLeaveType = leaveTypeRepository.save(leaveType);
        return leaveTypeMapper.toResponseDTO(updatedLeaveType);
    }

    public void deleteLeaveType(Long tenantId, Long id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveType not found with id " + id));
        leaveTypeRepository.delete(leaveType);
    }
}
