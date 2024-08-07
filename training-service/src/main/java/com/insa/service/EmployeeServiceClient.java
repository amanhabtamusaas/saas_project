package com.insa.service;

import com.insa.dto.apiDto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "EMPLOYEE-SERVICE", url = "${employee-service.base-url}")
public interface EmployeeServiceClient {

    @GetMapping("/api/employees/{tenant-id}/get")
    public EmployeeDto getEmployeeByEmployeeId(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId);

    @GetMapping("/api/employees/{tenant-id}/get-employee")
    public EmployeeDto getEmployeeByName(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("first-name") String firstName,
            @RequestParam(value = "middle-name", required = false) String middleName,
            @RequestParam(value = "last-name", required = false) String lastName);
}
