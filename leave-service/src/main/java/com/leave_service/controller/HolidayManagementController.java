package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.HolidayManagementRequest;
import com.leave_service.dto.response.HolidayManagementResponse;
import com.leave_service.service.HolidayManagementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/holiday-managements/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Holiday Management")
public class HolidayManagementController {

    private final HolidayManagementService holidayManagementService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-holiday-management")
    public ResponseEntity<?> createHolidayManagement(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody @Valid HolidayManagementRequest holidayManagementRequest) {

        permissionEvaluator.addHolidayManagementPermission();

        HolidayManagementResponse createdHolidayManagement = holidayManagementService
                .createHolidayManagement(tenantId, holidayManagementRequest);
        return ResponseEntity.ok(createdHolidayManagement);
    }

    @GetMapping("/get-all-holiday-managements")
    public ResponseEntity<?> getAllHolidayManagements(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllHolidayManagementsPermission();

        List<HolidayManagementResponse> holidayManagements = holidayManagementService
                .getAllHolidayManagements(tenantId);
        return ResponseEntity.ok(holidayManagements);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getHolidayManagementById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getHolidayManagementByIdPermission();

        HolidayManagementResponse holidayManagement = holidayManagementService
                .getHolidayManagementById(tenantId, id);
        return ResponseEntity.ok(holidayManagement);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHolidayManagement(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody @Valid HolidayManagementRequest holidayManagementRequest) {

        permissionEvaluator.updateHolidayManagementPermission();

        HolidayManagementResponse updatedHolidayManagement = holidayManagementService
                .updateHolidayManagement(tenantId, id, holidayManagementRequest);
        return ResponseEntity.ok(updatedHolidayManagement);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHolidayManagement(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteHolidayManagementPermission();

        holidayManagementService.deleteHolidayManagement(tenantId, id);
        return ResponseEntity.ok("Holiday Management deleted successfully!");
    }
}