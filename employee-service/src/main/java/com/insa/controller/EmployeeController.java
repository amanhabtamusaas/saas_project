package com.insa.controller;

import com.insa.dto.request.EmployeeRequest;
import com.insa.dto.response.EmployeeResponse;
import com.insa.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            description = "Create endpoint for employee",
            summary = "This is a summary for add employee endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Create",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    //@Hidden
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(
            @PathVariable("tenant-id") Long tenantId,
            @RequestPart("employee") EmployeeRequest employeeRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile file) {

        try {
            EmployeeResponse employee = employeeService
                    .addEmployee(tenantId, employeeRequest, file);
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the employee: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEmployees(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<EmployeeResponse> employees = employeeService
                    .getAllEmployees(tenantId);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the employees: " + e.getMessage());
        }
    }

    @GetMapping("/get/{employee-id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            EmployeeResponse employee = employeeService
                    .getEmployeeById(tenantId, employeeId);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the employee: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getEmployeeByEmployeeId(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId) {

        try {
            EmployeeResponse employee = employeeService
                    .getEmployeeByEmployeeId(tenantId, employeeId);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the employee: " + e.getMessage());
        }
    }

    @GetMapping("/get-employee")
    public ResponseEntity<?> getEmployeeByName(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("first-name") String firstName,
            @RequestParam(value = "middle-name", required = false) String middleName,
            @RequestParam(value = "last-name", required = false) String lastName) {

        try {
            EmployeeResponse employee = employeeService
                    .getEmployeeByName(tenantId, firstName, middleName, lastName);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the employee: " + e.getMessage());
        }
    }

    @GetMapping("/{department-id}/get")
    public ResponseEntity<?> getEmployeesByDepartmentId(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("department-id") Long departmentId) {

        try {
            List<EmployeeResponse> employees = employeeService
                    .getEmployeesByDepartmentId(tenantId, departmentId);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the employees: " + e.getMessage());
        }
    }

    @GetMapping("/download-image/{employee-id}")
    public ResponseEntity<?> getEmployeeProfileImageById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
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
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while downloading the profile image: " + e.getMessage());
        }
    }

    @PutMapping(value = "/update/{employee-id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @RequestPart("employee") EmployeeRequest employeeRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile file) {

        try {
            EmployeeResponse employee = employeeService
                    .updateEmployee(tenantId, employeeId, employeeRequest, file);
            return ResponseEntity.ok(employee);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the employee: " + e.getMessage());
        }
    }

    @DeleteMapping("delete/{employee-id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            employeeService.deleteEmployee(tenantId, employeeId);
            return ResponseEntity.ok("Employee Deleted Successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the employee: " + e.getMessage());
        }
    }
}