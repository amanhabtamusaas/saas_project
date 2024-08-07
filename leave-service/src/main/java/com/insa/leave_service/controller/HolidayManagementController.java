package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.HolidayManagementRequest;
import com.insa.leave_service.dto.response.HolidayManagementResponse;
import com.insa.leave_service.service.HolidayManagementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holiday-managements/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Holiday Management")
public class HolidayManagementController {

    private final HolidayManagementService holidayManagementService;

    @PostMapping("/add-holiday-management")
    public ResponseEntity<HolidayManagementResponse> createHolidayManagement(@PathVariable("tenantId") Long tenantId,
                                                                             @RequestBody @Valid HolidayManagementRequest holidayManagementRequest) {
        HolidayManagementResponse createdHolidayManagement = holidayManagementService.createHolidayManagement(tenantId, holidayManagementRequest);
        return  ResponseEntity.ok(createdHolidayManagement);
    }

    @GetMapping("/get-all-holiday-managements")
    public ResponseEntity<List<HolidayManagementResponse>> getAllHolidayManagements(@PathVariable("tenantId") Long tenantId) {
        List<HolidayManagementResponse> holidayManagements = holidayManagementService.getAllHolidayManagements(tenantId);
        return  ResponseEntity.ok(holidayManagements);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HolidayManagementResponse> getHolidayManagementById(@PathVariable("tenantId") Long tenantId, @PathVariable Long id) {
        HolidayManagementResponse holidayManagement = holidayManagementService.getHolidayManagementById(tenantId, id);
        return ResponseEntity.ok(holidayManagement );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HolidayManagementResponse> updateHolidayManagement(@PathVariable("tenantId") Long tenantId, @PathVariable Long id, @RequestBody @Valid HolidayManagementRequest holidayManagementRequest) {
        HolidayManagementResponse updatedHolidayManagement = holidayManagementService.updateHolidayManagement(tenantId, id, holidayManagementRequest);
        return  ResponseEntity.ok(updatedHolidayManagement);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHolidayManagement(@PathVariable("tenantId") Long tenantId, @PathVariable Long id) {
        holidayManagementService.deleteHolidayManagement(tenantId, id);
        return ResponseEntity.ok("Holiday Management deleted successfully!");
    }
}
