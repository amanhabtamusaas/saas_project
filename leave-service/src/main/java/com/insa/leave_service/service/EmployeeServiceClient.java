package com.insa.leave_service.service;

import com.insa.leave_service.dto.EmployeeDto;
import com.insa.leave_service.dto.ExperienceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "EMPLOYEE-SERVICE", url = "${employee-service.base-url}")
public interface EmployeeServiceClient {

    @GetMapping("/api/employees/{tenantId}/get/{employeeId}")
    public EmployeeDto getEmployeeById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("employeeId") Long employeeId);

    @GetMapping("/api/experiences/{tenantId}/{employeeId}/get-all")
    public List<ExperienceDto> getExperienceByEmployeeId(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("employeeId") Long employeeId);

    @GetMapping("/api/employees/{tenantId}/get-all")
    public List<EmployeeDto> getAllEmployees(
            @PathVariable("tenantId") Long tenantId);
}
