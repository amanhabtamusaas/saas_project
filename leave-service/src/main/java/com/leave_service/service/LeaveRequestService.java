package com.leave_service.service;

import com.leave_service.client.EmployeeServiceClient;
import com.leave_service.client.OrganizationServiceClient;
import com.leave_service.dto.EmployeeDto;
import com.leave_service.dto.TenantDto;
import com.leave_service.dto.request.LeaveRequestRequest;
import com.leave_service.dto.response.LeaveRequestResponse;
import com.leave_service.model.LeaveRequest;
import com.leave_service.exception.ResourceExistsException;
import com.leave_service.exception.ResourceNotFoundException;
import com.leave_service.mapper.LeaveRequestMapper;
import com.leave_service.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestMapper leaveRequestMapper;
    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;

    public LeaveRequestResponse createLeaveRequest(UUID tenantId, LeaveRequestRequest leaveRequestRequestDTO) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        if (leaveRequestRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("Leave request by this tenant " + tenant.getId() + " already exists");
        }

        // Retrieve employee information
        EmployeeDto employee = employeeServiceClient.getEmployeeById(tenantId, leaveRequestRequestDTO.getEmployeeId());
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with ID: " + leaveRequestRequestDTO.getEmployeeId());
        }

        LeaveRequest leaveRequest = leaveRequestMapper.toEntity(leaveRequestRequestDTO);
        // Set tenantId and employeeId in the leave request
        leaveRequest.setTenantId(tenantId);
        leaveRequest.setEmployeeId(leaveRequestRequestDTO.getEmployeeId());

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toResponseDTO(savedLeaveRequest);
    }

    public List<LeaveRequestResponse> getAllLeaveRequests(UUID tenantId) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        return leaveRequests.stream()
                .map(leaveRequestMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LeaveRequestResponse getLeaveRequestById(UUID tenantId, UUID id) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest not found with id " + id));
        return leaveRequestMapper.toResponseDTO(leaveRequest);
    }

    public LeaveRequestResponse updateLeaveRequest(UUID tenantId, UUID id,
                                                   LeaveRequestRequest leaveRequestRequestDTO) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest not found with id " + id));
        leaveRequestMapper.updateLeaveRequest(leaveRequest, leaveRequestRequestDTO);
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toResponseDTO(updatedLeaveRequest);
    }

    public void deleteLeaveRequest(UUID tenantId, UUID id) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest not found with id " + id));
        leaveRequestRepository.delete(leaveRequest);
    }
}