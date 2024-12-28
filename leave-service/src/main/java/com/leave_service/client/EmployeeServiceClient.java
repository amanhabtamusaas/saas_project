package com.leave_service.client;

import com.leave_service.dto.EmployeeDto;
import com.leave_service.dto.ExperienceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient("EMPLOYEE-SERVICE")
public interface EmployeeServiceClient {

    @GetMapping("/api/employees/{tenantId}/get/{employeeId}")
    public EmployeeDto getEmployeeById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("employeeId") UUID employeeId);

    @GetMapping("/api/experiences/{tenantId}/{employeeId}/get-all")
    public List<ExperienceDto> getExperienceByEmployeeId(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("employeeId") UUID employeeId);

    @GetMapping("/api/employees/{tenantId}/get-all")
    public List<EmployeeDto> getAllEmployees(
            @PathVariable("tenantId") UUID tenantId);
}
