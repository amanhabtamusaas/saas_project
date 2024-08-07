package com.insa.controller;

import com.insa.dto.requestDto.DepartmentRequest;
import com.insa.dto.responseDto.DepartmentResponse;
import com.insa.entity.Department;
import com.insa.exception.ResourceNotFoundException;
import com.insa.repository.DepartmentRepository;
import com.insa.service.DepartmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Department")
@SecurityRequirement(name = "Keycloak")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    @PostMapping("/add-department")
    public ResponseEntity<DepartmentResponse> createDepartment(
            @PathVariable Long tenantId,
            @RequestBody DepartmentRequest departmentRequest
    ) {

        DepartmentResponse departmentResponse = departmentService.createDepartment(tenantId, departmentRequest);
        return ResponseEntity.ok(departmentResponse);
    }
    @PutMapping("/{childDepartmentId}/parent/{newParentDepartmentId}")
    public ResponseEntity<DepartmentResponse> changeParentDepartment(
            @PathVariable Long tenantId,
            @PathVariable Long childDepartmentId,
            @PathVariable Long newParentDepartmentId) {
//        DepartmentResponse departmentResponse = departmentService.transferChildDepartment(tenantId,childDepartmentId, newParentDepartmentId);
//        return ResponseEntity.ok(departmentResponse);
        DepartmentResponse departmentResponse = departmentService.transferChildDepartment(tenantId,childDepartmentId, newParentDepartmentId);
        return ResponseEntity.ok(departmentResponse);

//        departmentService.transferChildDepartment(childDepartmentId, newParentDepartmentId);
//        return ResponseEntity.noContent().build();
    }
    // Endpoint to transfer a parent department to a new parent department
    @PostMapping("/{parentDepartmentId}/transfer/{newParentDepartmentId}")
    public ResponseEntity<DepartmentResponse> transferParentDepartment(
            @PathVariable Long tenantId,
            @PathVariable Long parentDepartmentId,
            @PathVariable Long newParentDepartmentId)
            {

                DepartmentResponse departmentResponse = departmentService.transferParentDepartment(tenantId,parentDepartmentId, newParentDepartmentId);
                return ResponseEntity.ok(departmentResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments(@PathVariable Long tenantId) {
        List<DepartmentResponse> departmentResponses = departmentService.getAllDepartments(tenantId);
        return ResponseEntity.ok(departmentResponses);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable Long tenantId,
            @PathVariable Long id) {
        DepartmentResponse departmentResponse = departmentService.getDepartmentById(id, tenantId);
        return ResponseEntity.ok(departmentResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable Long tenantId,
            @PathVariable Long id,
            @RequestBody DepartmentRequest departmentRequest) {
        DepartmentResponse departmentResponse = departmentService.updateDepartment(id, tenantId, departmentRequest);
        return ResponseEntity.ok(departmentResponse);
    }

//    @DeleteMapping("/remove-department/{parentId}")
//    public ResponseEntity<String> deleteDepartment(
//            @PathVariable Long tenantId,
//            @PathVariable Long parentId) {
//        departmentService.deleteDepartment(parentId, tenantId);
//        return ResponseEntity.ok("Parent-Department deleted successfully!");
//    }

//    @GetMapping("/{id}/parents")
//    public ResponseEntity<List<DepartmentResponse>> getAllParentDepartments(
//            @PathVariable Long tenantId,
//            @PathVariable Long id) {
//        List<DepartmentResponse> parentDepartments =  departmentService.getAllParentDepartments(id, tenantId);
//    }
//}

//    @GetMapping("/{parentId}/children")
//    public ResponseEntity<List<DepartmentResponse>> getAllChildDepartments(
//            @PathVariable Long tenantId,
//            @PathVariable Long parentId) {
//        List<DepartmentResponse> childDepartments = departmentService.getAllChildDepartments(parentId, tenantId);
//        return ResponseEntity.ok(childDepartments);
//    }

    @PostMapping("/{parentId}/sub-departments")
    public ResponseEntity<DepartmentResponse> addSubDepartment(
            @PathVariable Long tenantId,
            @PathVariable Long parentId,
            @RequestBody DepartmentRequest subDepartmentRequest) {
        DepartmentResponse departmentResponse = departmentService.addSubDepartment(parentId, tenantId, subDepartmentRequest);
        return ResponseEntity.ok(departmentResponse);
    }

    @DeleteMapping("/{parentId}/sub-departments/{subId}")
    public ResponseEntity<String> removeSubDepartment(
            @PathVariable Long tenantId,
            @PathVariable Long parentId,
            @PathVariable Long subId) {
        departmentService.removeSubDepartment(parentId, tenantId, subId);
        return ResponseEntity.ok("Sub-Department deleted successfully!");
    }



    @GetMapping("/parent-departments")
    public List<DepartmentResponse> getParentDepartments(@PathVariable Long tenantId) {
        return departmentService.getAllParentDepartments(tenantId);
    }


    @GetMapping("/{departmentId}/children")
    public List<Department> getAllChildDepartments(@PathVariable Long departmentId, @PathVariable Long tenantId) {
        return departmentService.getAllChildDepartments(departmentId,tenantId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id, @PathVariable Long tenantId) {
        departmentService.deleteDepartment(id, tenantId);
        return ResponseEntity.ok("Department deleted successfully!");
    }
}

