package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.DepartmentRequest;
import com.organization_service.dto.responseDto.DepartmentResponse;
import com.organization_service.model.Department;
import com.organization_service.repository.DepartmentRepository;
import com.organization_service.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Department")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-department")
    public ResponseEntity<?> createDepartment(
            @PathVariable UUID tenantId,
            @RequestBody DepartmentRequest departmentRequest) {

        permissionEvaluator.addDepartmentPermission();

        DepartmentResponse departmentResponse = departmentService
                .createDepartment(tenantId, departmentRequest);
        return ResponseEntity.ok(departmentResponse);
    }

    @PutMapping("/{childDepartmentId}/parent/{newParentDepartmentId}")
    public ResponseEntity<?> changeParentDepartment(
            @PathVariable UUID tenantId,
            @PathVariable UUID childDepartmentId,
            @PathVariable UUID newParentDepartmentId) {

        permissionEvaluator.transferSubDepartmentPermission();

        DepartmentResponse departmentResponse = departmentService
                .transferChildDepartment(tenantId, childDepartmentId, newParentDepartmentId);
        return ResponseEntity.ok(departmentResponse);
    }

    @PostMapping("/{parentDepartmentId}/transfer/{newParentDepartmentId}")
    public ResponseEntity<?> transferParentDepartment(
            @PathVariable UUID tenantId,
            @PathVariable UUID parentDepartmentId,
            @PathVariable UUID newParentDepartmentId) {

        permissionEvaluator.transferParentDepartmentPermission();

        DepartmentResponse departmentResponse = departmentService
                .transferParentDepartment(tenantId, parentDepartmentId, newParentDepartmentId);
        return ResponseEntity.ok(departmentResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDepartments(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllDepartmentsPermission();

        List<DepartmentResponse> departmentResponses = departmentService.getAllDepartments(tenantId);
        return ResponseEntity.ok(departmentResponses);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getDepartmentById(
            @PathVariable UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getDepartmentByIdPermission();

        DepartmentResponse departmentResponse = departmentService.getDepartmentById(id, tenantId);
        return ResponseEntity.ok(departmentResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDepartment(
            @PathVariable UUID tenantId,
            @PathVariable UUID id,
            @RequestBody DepartmentRequest departmentRequest) {

        permissionEvaluator.updateDepartmentPermission();

        DepartmentResponse departmentResponse = departmentService
                .updateDepartment(id, tenantId, departmentRequest);
        return ResponseEntity.ok(departmentResponse);
    }

    @PostMapping("/{parentId}/sub-departments")
    public ResponseEntity<?> addSubDepartment(
            @PathVariable UUID tenantId,
            @PathVariable UUID parentId,
            @RequestBody DepartmentRequest subDepartmentRequest) {

        permissionEvaluator.addSubDepartmentPermission();

        DepartmentResponse departmentResponse = departmentService
                .addSubDepartment(parentId, tenantId, subDepartmentRequest);
        return ResponseEntity.ok(departmentResponse);
    }

    @DeleteMapping("/{parentId}/sub-departments/{subId}")
    public ResponseEntity<String> removeSubDepartment(
            @PathVariable UUID tenantId,
            @PathVariable UUID parentId,
            @PathVariable UUID subId) {

        permissionEvaluator.deleteSubDepartmentPermission();

        departmentService.removeSubDepartment(parentId, tenantId, subId);
        return ResponseEntity.ok("Sub-Department deleted successfully!");
    }

    @GetMapping("/parent-departments")
    public ResponseEntity<?> getParentDepartments(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllParentDepartmentsPermission();

        List<DepartmentResponse> responses = departmentService.getAllParentDepartments(tenantId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{departmentId}/children")
    public ResponseEntity<?> getAllChildDepartments(
            @PathVariable UUID departmentId,
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllSubDepartmentsPermission();

        List<Department> departments = departmentService.getAllChildDepartments(departmentId, tenantId);
        return ResponseEntity.ok(departments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(
            @PathVariable UUID id,
            @PathVariable UUID tenantId) {

        permissionEvaluator.deleteDepartmentPermission();

        departmentService.deleteDepartment(id, tenantId);
        return ResponseEntity.ok("Department deleted successfully!");
    }
}