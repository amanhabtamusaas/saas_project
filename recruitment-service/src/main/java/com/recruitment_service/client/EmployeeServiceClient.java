package com.recruitment_service.client;

import com.recruitment_service.dto.clientDto.EmployeeDto;
import com.recruitment_service.dto.clientDto.LanguageNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient("EMPLOYEE-SERVICE")
public interface EmployeeServiceClient {

    @GetMapping("/api/employees/{tenant-id}/get")
    EmployeeDto getEmployeeByEmployeeId(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId);

    @GetMapping("/api/language-names/{tenant-id}/get/{language-id}")
    LanguageNameDto getLanguageName(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("language-id") UUID languageId);
}
