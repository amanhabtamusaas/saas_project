package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.responseDto.DepartmentHistoryResponse;
import com.organization_service.service.DepartmentHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments/history/{tenant-id}/{department-id}")
@RequiredArgsConstructor
@Tag(name = "Department History")
public class DepartmentHistoryController {

    private final DepartmentHistoryService departmentHistoryService;
    private final PermissionEvaluator permissionEvaluator;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDepartments(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("department-id") UUID departmentId) {

        permissionEvaluator.getAllDepartmentHistoriesPermission();

        List<DepartmentHistoryResponse> departments = departmentHistoryService
                .getAllDepartments(tenantId, departmentId);
        return ResponseEntity.ok(departments);
    }
}