package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.EmployeeRequest;
import com.employee_service.dto.response.EmployeeResponse;
import com.employee_service.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestPart("employee") EmployeeRequest employeeRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.addEmployeePermission();

        EmployeeResponse employee = employeeService
                .addEmployee(tenantId, employeeRequest, file);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEmployees(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllEmployeesPermission();

        List<EmployeeResponse> employees = employeeService
                .getAllEmployees(tenantId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/get/{employee-id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getEmployeeByIdPermission();

        EmployeeResponse employee = employeeService
                .getEmployeeById(tenantId, employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getEmployeeByEmployeeId(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getEmployeeByEmployeeIdPermission();

        EmployeeResponse employee = employeeService
                .getEmployeeByEmployeeId(tenantId, employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/get-employee")
    public ResponseEntity<?> getEmployeeByName(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("first-name") String firstName,
            @RequestParam(value = "middle-name", required = false) String middleName,
            @RequestParam(value = "last-name", required = false) String lastName) {

        permissionEvaluator.getEmployeeByNamePermission();

        EmployeeResponse employee = employeeService
                .getEmployeeByName(tenantId, firstName, middleName, lastName);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{department-id}/get")
    public ResponseEntity<?> getEmployeesByDepartmentId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("department-id") UUID departmentId) {

        permissionEvaluator.getEmployeesByDepartmentIdPermission();

        List<EmployeeResponse> employees = employeeService
                .getEmployeesByDepartmentId(tenantId, departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/download-image/{employee-id}")
    public ResponseEntity<?> getEmployeeProfileImageById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.downloadEmployeeImagePermission();

        EmployeeResponse employee = employeeService
                .getEmployeeById(tenantId, employeeId);
        if (employee == null || employee.getProfileImageType() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee profile image not found");
        }
        MediaType mediaType = MediaType.valueOf(employee.getProfileImageType());
        byte[] profileImage = employeeService
                .getEmployeeProfileImageById(tenantId, employeeId, mediaType);
        return ResponseEntity.ok().contentType(mediaType).body(profileImage);
    }

    @PutMapping(value = "/update/{employee-id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestPart("employee") EmployeeRequest employeeRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.updateEmployeePermission();

        EmployeeResponse employee = employeeService
                .updateEmployee(tenantId, employeeId, employeeRequest, file);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("delete/{employee-id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.deleteEmployeePermission();

        employeeService.deleteEmployee(tenantId, employeeId);
        return ResponseEntity.ok("Employee Deleted Successfully");
    }
}