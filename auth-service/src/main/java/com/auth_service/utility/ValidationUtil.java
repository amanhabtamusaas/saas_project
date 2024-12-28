package com.auth_service.utility;

import com.auth_service.dto.clientDto.EmployeeDto;
import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.exception.ResourceNotFoundException;
import com.auth_service.client.EmployeeServiceClient;
import com.auth_service.client.OrganizationServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final OrganizationServiceClient organizationServiceClient;
    private final EmployeeServiceClient employeeServiceClient;

    public TenantDto getTenantById(UUID tenantId) {

        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenant == null) {
            throw new ResourceNotFoundException(
                    "Tenant not found with id '" + tenantId + "'");
        }
        return tenant;
    }

    public EmployeeDto getEmployeeByEmployeeId(UUID tenantId,
                                               String employeeId) {

        EmployeeDto employee = employeeServiceClient
                .getEmployeeByEmployeeId(tenantId, employeeId);
        if (employee == null) {
            throw new ResourceNotFoundException(
                    "Employee not found with id '" + employeeId + "'");
        }
        return employee;
    }
}
