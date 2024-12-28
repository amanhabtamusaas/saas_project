package com.leave_service.mapper;

import com.leave_service.dto.request.BudgetYearRequest;
import com.leave_service.dto.response.BudgetYearResponse;
import com.leave_service.model.BudgetYear;
import com.leave_service.client.OrganizationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetYearMapper {

    private final OrganizationServiceClient organizationServiceClient;

    /**
     * Maps a BudgetYearRequest to a BudgetYear entity.
     *
     * @param request the BudgetYearRequest to map
     * @return the mapped BudgetYear entity
     */
    public BudgetYear mapToEntity(BudgetYearRequest request) {
        // Ensure the tenant exists using the TenantServiceClient
        // Uncomment the following line if tenant validation is required
//         tenantServiceClient.getTenantById(request.getTenantId());

        BudgetYear budgetYear = new BudgetYear();
//        budgetYear.setYear(request.getYear());
        budgetYear.setBudgetYear(request.getBudgetYear());

        budgetYear.setDescription(request.getDescription());
        budgetYear.setActive(request.isActive());
        // Uncomment the following line if tenant ID should be set
//        budgetYear.setTenantId(request.getTenantId());
        return budgetYear;
    }

    /**
     * Maps a BudgetYear entity to a BudgetYearResponse.
     *
     * @param entity the BudgetYear entity to map
     * @return the mapped BudgetYearResponse
     */
    public BudgetYearResponse mapToDto(BudgetYear entity) {
        BudgetYearResponse response = new BudgetYearResponse();
        response.setId(entity.getId());
//        response.setYear(entity.getYear());
        response.setBudgetYear(entity.getBudgetYear());

        response.setDescription(entity.getDescription());
        response.setActive(entity.isActive());
        response.setTenantId(entity.getTenantId());
        return response;
    }

    /**
     * Updates an existing BudgetYear entity with data from a BudgetYearRequest.
     *
     * @param budgetYear the existing BudgetYear entity to update
     * @param request the BudgetYearRequest containing updated data
     */
    public void updateBudgetYear(BudgetYear budgetYear, BudgetYearRequest request) {
        // Ensure the tenant exists using the TenantServiceClient
        // Uncomment the following line if tenant validation is required
        // tenantServiceClient.getTenantById(request.getTenantId());

//        budgetYear.setYear(request.getYear());
        budgetYear.setBudgetYear(request.getBudgetYear());

        budgetYear.setDescription(request.getDescription());
        budgetYear.setActive(request.isActive());
        // Uncomment the following line if tenant ID should be updated
        // budgetYear.setTenantId(request.getTenantId());
    }
}