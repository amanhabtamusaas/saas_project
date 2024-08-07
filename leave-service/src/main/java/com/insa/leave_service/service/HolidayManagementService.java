package com.insa.leave_service.service;

import com.insa.leave_service.dto.TenantDto;
import com.insa.leave_service.dto.request.HolidayManagementRequest;
import com.insa.leave_service.dto.response.HolidayManagementResponse;
import com.insa.leave_service.entity.HolidayManagement;
import com.insa.leave_service.exception.ResourceExistsException;
import com.insa.leave_service.exception.ResourceNotFoundException;
import com.insa.leave_service.mapper.HolidayManagementMapper;
import com.insa.leave_service.repository.HolidayManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayManagementService {

    private final HolidayManagementRepository holidayManagementRepository;
    private final HolidayManagementMapper holidayManagementMapper;
    private final TenantServiceClient tenantServiceClient;

    public HolidayManagementResponse createHolidayManagement(Long tenantId, HolidayManagementRequest holidayManagementRequest) {
        // Validate the tenant ID
        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }
//        if (holidayManagementRepository.existsByBudgetYearAndTenantId(holidayManagementRequest.get(),tenant.getId())) {
//            throw new ResourceExistsException("Holiday with Name " + holidayRequest.getHolidayName() + " already exists");
//        }


        // Map the request to an entity
        HolidayManagement holidayManagement = holidayManagementMapper.mapToEntity(holidayManagementRequest);
        holidayManagement.setTenantId(tenant.getId());

        // Save the holiday management entity
        HolidayManagement savedHolidayManagement = holidayManagementRepository.save(holidayManagement);

        // Map the saved entity back to a response DTO
        return holidayManagementMapper.mapToDto(savedHolidayManagement);
    }

    public List<HolidayManagementResponse> getAllHolidayManagements(Long tenantId) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Retrieve all holiday management entities
        List<HolidayManagement> holidayManagements = holidayManagementRepository.findAll();

        // Map the entities to response DTOs
        return holidayManagements.stream()
                .map(holidayManagementMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HolidayManagementResponse getHolidayManagementById(Long tenantId, Long id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Map the entity to a response DTO
        return holidayManagementMapper.mapToDto(holidayManagement);
    }

    public HolidayManagementResponse updateHolidayManagement(Long tenantId, Long id, HolidayManagementRequest holidayManagementRequest) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Update the holiday management entity with data from the request
        holidayManagementMapper.updateHolidayManagement(holidayManagement, holidayManagementRequest);

        // Save the updated holiday management entity
        HolidayManagement updatedHolidayManagement = holidayManagementRepository.save(holidayManagement);

        // Map the updated entity back to a response DTO
        return holidayManagementMapper.mapToDto(updatedHolidayManagement);
    }

    public void deleteHolidayManagement(Long tenantId, Long id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the holiday management entity by ID
        HolidayManagement holidayManagement = holidayManagementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HolidayManagement not found with ID: " + id));

        // Delete the holiday management entity
        holidayManagementRepository.delete(holidayManagement);
    }
}

