package com.organization_service.controller;

import com.organization_service.config.PermissionEvaluator;
import com.organization_service.dto.requestDto.WorkUnitRequest;
import com.organization_service.dto.responseDto.WorkUnitResponse;
import com.organization_service.service.WorkUnitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/work-units/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Work Unit")
public class WorkUnitController {

    private final WorkUnitService workUnitService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-work-unit")
    public ResponseEntity<?> createWorkUnit(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody WorkUnitRequest workUnitRequest) {

        permissionEvaluator.addWorkUnitPermission();

        WorkUnitResponse workUnit = workUnitService.createWorkUnit(tenantId, workUnitRequest);
        return new ResponseEntity<>(workUnit, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllWorkUnits(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllWorkUnitsPermission();

        List<WorkUnitResponse> workUnits = workUnitService.getAllWorkUnits(tenantId);
        return ResponseEntity.ok(workUnits);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getWorkUnitById(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getWorkUnitByIdPermission();

        WorkUnitResponse workUnit = workUnitService.getWorkUnitById(id, tenantId);
        return ResponseEntity.ok(workUnit);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWorkUnit(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody WorkUnitRequest workUnitRequest) {

        permissionEvaluator.updateWorkUnitPermission();

        WorkUnitResponse workUnit = workUnitService.updateWorkUnit(id, tenantId, workUnitRequest);
        return ResponseEntity.ok(workUnit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkUnit(
            @PathVariable UUID id,
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.deleteWorkUnitPermission();

        workUnitService.deleteWorkUnit(id, tenantId);
        return ResponseEntity.ok("WorkUnit deleted successfully!");
    }
}