package com.leave_service.controller;

import com.leave_service.config.PermissionEvaluator;
import com.leave_service.dto.request.HolidayRequest;
import com.leave_service.dto.response.HolidayResponse;
import com.leave_service.service.HolidayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/holidays/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Holiday")
public class HolidayController {

    private final HolidayService holidayService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add-holiday")
    public ResponseEntity<?> createHoliday(
            @PathVariable("tenantId") UUID tenantId,
            @RequestBody @Valid HolidayRequest holidayRequest) {

        permissionEvaluator.addHolidayPermission();

        HolidayResponse createdHoliday = holidayService.createHoliday(tenantId, holidayRequest);
        return new ResponseEntity<>(createdHoliday, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-holidays")
    public ResponseEntity<?> getAllHolidays(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllHolidaysPermission();

        List<HolidayResponse> holidays = holidayService.getAllHolidays(tenantId);
        return ResponseEntity.ok(holidays);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getHolidayById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.getHolidayByIdPermission();

        HolidayResponse holiday = holidayService.getHolidayById(tenantId, id);
        return ResponseEntity.ok(holiday);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHoliday(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id,
            @RequestBody @Valid HolidayRequest holidayRequest) {

        permissionEvaluator.updateHolidayPermission();

        HolidayResponse updatedHoliday = holidayService.updateHoliday(tenantId, id, holidayRequest);
        return ResponseEntity.ok(updatedHoliday);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHoliday(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable UUID id) {

        permissionEvaluator.deleteHolidayPermission();

        holidayService.deleteHoliday(tenantId, id);
        return ResponseEntity.ok("Holiday deleted successfully!");
    }
}