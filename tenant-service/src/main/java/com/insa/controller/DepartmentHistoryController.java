package com.insa.controller;

import com.insa.dto.responseDto.DepartmentHistoryResponse;
import com.insa.dto.responseDto.DepartmentResponse;
import com.insa.service.DepartmentHistoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments/history/{tenant-id}/{department-id}")
@RequiredArgsConstructor
@Tag(name = "Department History")
@SecurityRequirement(name = "Keycloak")
public class DepartmentHistoryController {
    private final DepartmentHistoryService departmentHistoryService;
    @GetMapping("/get-all")
    public ResponseEntity<List<DepartmentHistoryResponse>> getAllDepartments(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("department-id") Long departmentId) {
        List<DepartmentHistoryResponse> departments = departmentHistoryService.getAllDepartments (tenantId,departmentId);
        return ResponseEntity.ok (departments);
    }
}
