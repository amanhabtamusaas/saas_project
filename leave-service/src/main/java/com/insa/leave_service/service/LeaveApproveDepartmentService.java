package com.insa.leave_service.service;

import com.insa.leave_service.dto.TenantDto;
import com.insa.leave_service.dto.request.LeaveApproveDepartmentRequest;
import com.insa.leave_service.dto.response.LeaveApproveDepartmentResponse;
import com.insa.leave_service.entity.LeaveApproveDepartment;
import com.insa.leave_service.exception.ResourceExistsException;
import com.insa.leave_service.exception.ResourceNotFoundException;
import com.insa.leave_service.mapper.LeaveApproveDepartmentMapper;
import com.insa.leave_service.repository.LeaveApproveDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveApproveDepartmentService {

    private final LeaveApproveDepartmentRepository leaveApproveDepartmentRepository;
    private final LeaveApproveDepartmentMapper leaveApproveDepartmentMapper;
    private final TenantServiceClient tenantServiceClient;

    public LeaveApproveDepartmentResponse createLeaveApproveDepartment(Long tenantId, LeaveApproveDepartmentRequest request) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (leaveApproveDepartmentRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("leave approved by this tenant   " + tenant.getId() +  " already exists");
        }


        // Map the request to an entity
        LeaveApproveDepartment leaveApproveDepartment = leaveApproveDepartmentMapper.toEntity(request);
        leaveApproveDepartment.setTenantId(tenant.getId());

        // Save the leave approve department entity
        LeaveApproveDepartment savedLeaveApproveDepartment = leaveApproveDepartmentRepository.save(leaveApproveDepartment);

        // Map the saved entity back to a response DTO
        return leaveApproveDepartmentMapper.toDto(savedLeaveApproveDepartment);
    }

    public List<LeaveApproveDepartmentResponse> getAllLeaveApproveDepartments(Long tenantId) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Retrieve all leave approve department entities
        List<LeaveApproveDepartment> leaveApproveDepartments = leaveApproveDepartmentRepository.findAll();

        // Map the entities to response DTOs
        return leaveApproveDepartments.stream()
                .map(leaveApproveDepartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public LeaveApproveDepartmentResponse getLeaveApproveDepartmentById(Long tenantId, Long id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the leave approve department entity by ID
        LeaveApproveDepartment leaveApproveDepartment = leaveApproveDepartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveDepartment not found with ID: " + id));

        // Map the entity to a response DTO
        return leaveApproveDepartmentMapper.toDto(leaveApproveDepartment);
    }

    public LeaveApproveDepartmentResponse updateLeaveApproveDepartment(Long tenantId, Long id, LeaveApproveDepartmentRequest request) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the leave approve department entity by ID
        LeaveApproveDepartment leaveApproveDepartment = leaveApproveDepartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveDepartment not found with ID: " + id));

        // Update the leave approve department entity with data from the request
        leaveApproveDepartmentMapper.updateLeaveApproveDepartment(leaveApproveDepartment, request);

        // Save the updated leave approve department entity
        LeaveApproveDepartment updatedLeaveApproveDepartment = leaveApproveDepartmentRepository.save(leaveApproveDepartment);

        // Map the updated entity back to a response DTO
        return leaveApproveDepartmentMapper.toDto(updatedLeaveApproveDepartment);
    }

    public void deleteLeaveApproveDepartment(Long tenantId, Long id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the leave approve department entity by ID
        LeaveApproveDepartment leaveApproveDepartment = leaveApproveDepartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveDepartment not found with ID: " + id));

        // Delete the leave approve department entity
        leaveApproveDepartmentRepository.delete(leaveApproveDepartment);
    }


}
