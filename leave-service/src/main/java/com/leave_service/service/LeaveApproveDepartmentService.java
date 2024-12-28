package com.leave_service.service;

import com.leave_service.client.OrganizationServiceClient;
import com.leave_service.dto.TenantDto;
import com.leave_service.dto.request.LeaveApproveDepartmentRequest;
import com.leave_service.dto.response.LeaveApproveDepartmentResponse;
import com.leave_service.model.LeaveApproveDepartment;
import com.leave_service.exception.ResourceExistsException;
import com.leave_service.exception.ResourceNotFoundException;
import com.leave_service.mapper.LeaveApproveDepartmentMapper;
import com.leave_service.repository.LeaveApproveDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveApproveDepartmentService {

    private final LeaveApproveDepartmentRepository leaveApproveDepartmentRepository;
    private final LeaveApproveDepartmentMapper leaveApproveDepartmentMapper;
    private final OrganizationServiceClient organizationServiceClient;

    public LeaveApproveDepartmentResponse createLeaveApproveDepartment(UUID tenantId,
                                                                       LeaveApproveDepartmentRequest request) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (leaveApproveDepartmentRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("Leave approved by this tenant " +
                    tenant.getId() + " already exists");
        }

        // Map the request to an entity
        LeaveApproveDepartment leaveApproveDepartment = leaveApproveDepartmentMapper.toEntity(request);
        leaveApproveDepartment.setTenantId(tenant.getId());

        // Save the leave approve department entity
        LeaveApproveDepartment savedLeaveApproveDepartment = leaveApproveDepartmentRepository.save(leaveApproveDepartment);

        // Map the saved entity back to a response DTO
        return leaveApproveDepartmentMapper.toDto(savedLeaveApproveDepartment);
    }

    public List<LeaveApproveDepartmentResponse> getAllLeaveApproveDepartments(UUID tenantId) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Retrieve all leave approve department entities
        List<LeaveApproveDepartment> leaveApproveDepartments = leaveApproveDepartmentRepository.findAll();

        // Map the entities to response DTOs
        return leaveApproveDepartments.stream()
                .map(leaveApproveDepartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public LeaveApproveDepartmentResponse getLeaveApproveDepartmentById(UUID tenantId, UUID id) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the leave approve department entity by ID
        LeaveApproveDepartment leaveApproveDepartment = leaveApproveDepartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveDepartment not found with ID: " + id));

        // Map the entity to a response DTO
        return leaveApproveDepartmentMapper.toDto(leaveApproveDepartment);
    }

    public LeaveApproveDepartmentResponse updateLeaveApproveDepartment(UUID tenantId, UUID id,
                                                                       LeaveApproveDepartmentRequest request) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

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

    public void deleteLeaveApproveDepartment(UUID tenantId, UUID id) {

        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the leave approve department entity by ID
        LeaveApproveDepartment leaveApproveDepartment = leaveApproveDepartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveApproveDepartment not found with ID: " + id));

        // Delete the leave approve department entity
        leaveApproveDepartmentRepository.delete(leaveApproveDepartment);
    }
}