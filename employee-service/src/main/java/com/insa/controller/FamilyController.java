package com.insa.controller;

import com.insa.dto.request.FamilyRequest;
import com.insa.dto.response.FamilyResponse;
import com.insa.service.FamilyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/families/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Family")
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addFamily(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @RequestBody FamilyRequest familyRequest) {

        try {
            FamilyResponse family = familyService
                    .addFamily (tenantId, employeeId, familyRequest);
            return new ResponseEntity<> (family, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the family: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllFamilies(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            List<FamilyResponse> families = familyService
                    .getAllFamilies(tenantId, employeeId);
            return ResponseEntity.ok(families);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the families: " + e.getMessage());
        }
    }

    @GetMapping("/get/employee-families")
    public ResponseEntity<?> getEmployeeFamilies(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId) {

        try {
            List<FamilyResponse> families = familyService
                    .getEmployeeFamilies(tenantId, employeeId);
            return ResponseEntity.ok(families);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the families: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get/{family-id}")
    public ResponseEntity<?> getFamilyById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("family-id") Long familyId) {

        try {
            FamilyResponse family = familyService
                    .getFamilyById(tenantId, employeeId, familyId);
            return ResponseEntity.ok(family);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the family: " + e.getMessage());
        }
    }

    @PutMapping("/{employee-id}/update/{family-id}")
    public ResponseEntity<?> updateFamily(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("family-id") Long familyId,
            @RequestBody FamilyRequest familyRequest) {

        try {
            FamilyResponse family = familyService
                    .updateFamily(tenantId, employeeId, familyId, familyRequest);
            return ResponseEntity.ok(family);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the family: " + e.getMessage());
        }
    }

    @DeleteMapping("/{employee-id}/delete/{family-id}")
    public ResponseEntity<?> deleteFamily(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("family-id") Long familyId) {

        try {
            familyService.deleteFamily(tenantId, employeeId, familyId);
            return ResponseEntity.ok("Family deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the family: " + e.getMessage());
        }
    }
}
