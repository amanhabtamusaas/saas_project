package com.insa.controller;

import com.insa.dto.requestDto.DepartmentTypeRequest;
import com.insa.dto.requestDto.LocationTypeRequest;
import com.insa.dto.responseDto.DepartmentTypeResponse;
import com.insa.dto.responseDto.LocationTypeResponse;
import com.insa.entity.Department;
import com.insa.service.DepartmentTypeService;
import com.insa.service.LocationTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department-types/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Department Type")
@SecurityRequirement(name = "Keycloak")
public class DepartmentTypeController {
    private final DepartmentTypeService departmentTypeService;

    @PostMapping("/add-department-type")
    public ResponseEntity<DepartmentTypeResponse> createDepartmentType(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody DepartmentTypeRequest departmentTypeRequest) {

        DepartmentTypeResponse departmentType = departmentTypeService
                .createDepartmentType (tenantId, departmentTypeRequest);
        return new ResponseEntity<> (departmentType, HttpStatus.CREATED);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<DepartmentTypeResponse>> getAllLocationTypes(
            @PathVariable("tenant-id") Long tenantId) {
        List<DepartmentTypeResponse> locationTypes = departmentTypeService.getAllDepartmentTypes (tenantId);
        return ResponseEntity.ok (locationTypes);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<DepartmentTypeResponse> getDepartmentTypeByIdTypeById(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        DepartmentTypeResponse location = departmentTypeService.getDepartmentTypeById (id, tenantId);
        return ResponseEntity.ok (location);
    }

    @PutMapping("/update-departmentType/{id}")
    public ResponseEntity<DepartmentTypeResponse> updateDepartmentType(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody DepartmentTypeRequest departmentTypeRequest) {

        DepartmentTypeResponse location = departmentTypeService
                .updateDepartmentType (id, tenantId, departmentTypeRequest);
        return ResponseEntity.ok (location);
    }

    @DeleteMapping("/delete-departmentType/{id}")
    public ResponseEntity<String> deleteDepartmentType(
            @PathVariable Long id,
            @PathVariable("tenant-id") Long tenantId) {

        departmentTypeService.deleteDepartmentType (id, tenantId);
        return ResponseEntity.ok ("DepartmentType deleted successfully!");
    }
}
