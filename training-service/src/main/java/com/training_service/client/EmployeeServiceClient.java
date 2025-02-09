package com.training_service.client;

import com.training_service.dto.clientDto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("EMPLOYEE-SERVICE")
public interface EmployeeServiceClient {

    @GetMapping("/api/employees/{tenant-id}/get")
    public EmployeeDto getEmployeeByEmployeeId(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId);

    @GetMapping("/api/employees/{tenant-id}/get-employee")
    public EmployeeDto getEmployeeByName(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("first-name") String firstName,
            @RequestParam(value = "middle-name", required = false) String middleName,
            @RequestParam(value = "last-name", required = false) String lastName);
}
