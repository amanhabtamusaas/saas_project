package com.insa.controller;

import com.insa.dto.requestDto.WorkUnitRequest;
import com.insa.dto.responseDto.WorkUnitResponse;
import com.insa.service.WorkUnitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-units/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Work Unit")
@SecurityRequirement(name = "Keycloak")
public class WorkUnitController {

    private final WorkUnitService workUnitService;

    @PostMapping("/add-work-unt")
    public ResponseEntity<WorkUnitResponse> createWorkUnit(
            @PathVariable("tenantId") Long tenantId,
            @RequestBody WorkUnitRequest workUnitRequest) {

        WorkUnitResponse workUnit = workUnitService.createWorkUnit(tenantId, workUnitRequest);
        return new ResponseEntity<>(workUnit, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<WorkUnitResponse>> getAllWorkUnits(
            @PathVariable("tenantId") Long tenantId) {
        List<WorkUnitResponse> workUnits = workUnitService.getAllWorkUnits(tenantId);
        return ResponseEntity.ok(workUnits);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<WorkUnitResponse> getWorkUnitById(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        WorkUnitResponse workUnit = workUnitService.getWorkUnitById(id, tenantId);
        return ResponseEntity.ok(workUnit);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkUnitResponse> updateWorkUnit(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId,
            @RequestBody WorkUnitRequest workUnitRequest) {

        WorkUnitResponse workUnit = workUnitService.updateWorkUnit(id, tenantId, workUnitRequest);
        return ResponseEntity.ok(workUnit);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorkUnit(
            @PathVariable Long id,
            @PathVariable("tenantId") Long tenantId) {

        workUnitService.deleteWorkUnit(id, tenantId);
        return ResponseEntity.ok("WorkUnit deleted successfully!");
    }
}
