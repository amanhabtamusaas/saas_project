package com.leave_service.service;

import com.leave_service.client.EmployeeServiceClient;
import com.leave_service.client.OrganizationServiceClient;
import com.leave_service.dto.EmployeeDto;
import com.leave_service.dto.TenantDto;
import com.leave_service.dto.request.LeaveScheduleRequest;
import com.leave_service.dto.response.LeaveScheduleResponse;
import com.leave_service.model.LeaveSchedule;
import com.leave_service.exception.ResourceExistsException;
import com.leave_service.exception.ResourceNotFoundException;
import com.leave_service.mapper.LeaveScheduleMapper;
import com.leave_service.repository.LeaveScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveScheduleService {

    private final LeaveScheduleRepository leaveScheduleRepository;
    private final LeaveScheduleMapper leaveScheduleMapper;
    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;

    public LeaveScheduleResponse createLeaveSchedule(UUID tenantId, LeaveScheduleRequest request) {
        validateTenantId(tenantId);

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (leaveScheduleRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("Leave schedule by this tenant " + tenant.getId() + " already exists");
        }

        // Validate employee ID
        EmployeeDto requester = employeeServiceClient.getEmployeeById(tenantId, request.getEmployeeId());
        if (requester == null) {
            throw new ResourceNotFoundException("Employee not found with ID: " + request.getEmployeeId());
        }

        // Map the request to an entity
        LeaveSchedule leaveSchedule = leaveScheduleMapper.toLeaveSchedule(request);
        leaveSchedule.setTenantId(tenant.getId());

        // Save the leave schedule entity
        LeaveSchedule savedLeaveSchedule = leaveScheduleRepository.save(leaveSchedule);

        // Map the saved entity back to a response DTO
        return leaveScheduleMapper.toLeaveScheduleResponse(savedLeaveSchedule);
    }

    public List<LeaveScheduleResponse> getAllLeaveSchedules(UUID tenantId) {
        validateTenantId(tenantId);

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Retrieve all leave schedule entities
        List<LeaveSchedule> leaveSchedules = leaveScheduleRepository.findAll();

        // Map the entities to response DTOs
        return leaveSchedules.stream()
                .map(leaveScheduleMapper::toLeaveScheduleResponse)
                .collect(Collectors.toList());
    }

    public LeaveScheduleResponse getLeaveScheduleById(UUID tenantId, UUID id) {
        validateTenantId(tenantId);

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the leave schedule entity by ID
        LeaveSchedule leaveSchedule = leaveScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveSchedule not found with ID: " + id));

        // Map the entity to a response DTO
        return leaveScheduleMapper.toLeaveScheduleResponse(leaveSchedule);
    }

    public LeaveScheduleResponse updateLeaveSchedule(UUID tenantId, UUID id, LeaveScheduleRequest request) {
        validateTenantId(tenantId);

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the leave schedule entity by ID
        LeaveSchedule leaveSchedule = leaveScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveSchedule not found with ID: " + id));

        // Update the leave schedule entity with data from the request
        leaveScheduleMapper.updateLeaveSchedule(leaveSchedule, request);

        // Save the updated leave schedule entity
        LeaveSchedule updatedLeaveSchedule = leaveScheduleRepository.save(leaveSchedule);

        // Map the updated entity back to a response DTO
        return leaveScheduleMapper.toLeaveScheduleResponse(updatedLeaveSchedule);
    }

    public void deleteLeaveSchedule(UUID tenantId, UUID id) {
        validateTenantId(tenantId);

        // Retrieve tenant information
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        // Find the leave schedule entity by ID
        LeaveSchedule leaveSchedule = leaveScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveSchedule not found with ID: " + id));

        // Delete the leave schedule entity
        leaveScheduleRepository.delete(leaveSchedule);
    }

    private void validateTenantId(UUID tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }
    }
}