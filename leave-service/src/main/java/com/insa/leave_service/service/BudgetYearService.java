package com.insa.leave_service.service;

import com.insa.leave_service.dto.TenantDto;
import com.insa.leave_service.dto.request.BudgetYearRequest;
import com.insa.leave_service.dto.response.BudgetYearResponse;
import com.insa.leave_service.entity.BudgetYear;
import com.insa.leave_service.exception.ResourceExistsException;
import com.insa.leave_service.exception.ResourceNotFoundException;
import com.insa.leave_service.mapper.BudgetYearMapper;
import com.insa.leave_service.repository.BudgetYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetYearService {

    private final BudgetYearRepository budgetYearRepository;
    private final BudgetYearMapper budgetYearMapper;
    private final TenantServiceClient tenantServiceClient;

    public BudgetYearResponse createBudgetYear(Long tenantId, BudgetYearRequest budgetYearRequest) {
        // Validate the tenant ID
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }
        if (budgetYearRepository.existsByBudgetYearAndTenantId(budgetYearRequest.getBudgetYear(),tenant.getId())) {
            throw new ResourceExistsException("BudgetYear with Name " + budgetYearRequest.getBudgetYear() +  " already exists");
        }


        // Retrieve tenant information

        // Map the request to an entity
        BudgetYear budgetYear = budgetYearMapper.mapToEntity(budgetYearRequest);
        budgetYear.setTenantId(tenant.getId());

        // Save the budget year entity
        BudgetYear savedBudgetYear = budgetYearRepository.save(budgetYear);

        // Map the saved entity back to a response DTO
        return budgetYearMapper.mapToDto(savedBudgetYear);
    }

    public List<BudgetYearResponse> getAllBudgetYears(Long tenantId) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Retrieve all budget years
        List<BudgetYear> budgetYears = budgetYearRepository.findAll();

        // Map the entities to response DTOs
        return budgetYears.stream()
                .map(budgetYearMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public BudgetYearResponse getBudgetYearById(Long tenantId, Long id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the budget year entity by ID
        BudgetYear budgetYear = budgetYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetYear not found with ID: " + id));

        // Map the entity to a response DTO
        return budgetYearMapper.mapToDto(budgetYear);
    }

    public BudgetYearResponse updateBudgetYear(Long tenantId, Long id, BudgetYearRequest budgetYearRequest) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the budget year entity by ID
        BudgetYear budgetYear = budgetYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetYear not found with ID: " + id));

        // Update the budget year entity with data from the request
        budgetYearMapper.updateBudgetYear(budgetYear, budgetYearRequest);

        // Save the updated budget year entity
        BudgetYear updatedBudgetYear = budgetYearRepository.save(budgetYear);

        // Map the updated entity back to a response DTO
        return budgetYearMapper.mapToDto(updatedBudgetYear);
    }

    public void deleteBudgetYear(Long tenantId, Long id) {
        // Validate the tenant ID
        if (tenantId == null) {
            throw new IllegalArgumentException("Tenant ID cannot be null");
        }

        // Retrieve tenant information
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        // Find the budget year entity by ID
        BudgetYear budgetYear = budgetYearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetYear not found with ID: " + id));

        // Delete the budget year entity
        budgetYearRepository.delete(budgetYear);
    }


}
