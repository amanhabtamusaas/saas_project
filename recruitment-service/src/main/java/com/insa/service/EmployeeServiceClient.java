package com.insa.service;

import com.insa.dto.apiDto.EmployeeDto;
import com.insa.dto.apiDto.LanguageNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "EMPLOYEE-SERVICE", url = "${employee-service.base-url}")
public interface EmployeeServiceClient {

    @GetMapping("/api/employees/{tenant-id}/get")
    public EmployeeDto getEmployeeByEmployeeId(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId);

    @GetMapping("/api/language-names/{tenant-id}/get/{language-id}")
    public LanguageNameDto getLanguageName(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("language-id") Long languageId);
}
