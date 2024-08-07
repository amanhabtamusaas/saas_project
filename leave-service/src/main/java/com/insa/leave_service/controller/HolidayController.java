package com.insa.leave_service.controller;

import com.insa.leave_service.dto.request.HolidayRequest;
import com.insa.leave_service.dto.response.HolidayResponse;
import com.insa.leave_service.service.HolidayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holidays/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Holiday")
public class HolidayController {

    private final HolidayService holidayService;

    @PostMapping("/add-holiday")
    public ResponseEntity<HolidayResponse> createHoliday(@PathVariable("tenantId") Long tenantId,
                                                         @RequestBody @Valid HolidayRequest holidayRequest) {
        HolidayResponse createdHoliday = holidayService.createHoliday(tenantId, holidayRequest);
//        return new ResponseEntity<>(createdHoliday, HttpStatus.CREATED);

        return ResponseEntity.ok(createdHoliday);
    }

    @GetMapping("/get-all-holidays")
    public ResponseEntity<List<HolidayResponse>> getAllHolidays(@PathVariable("tenantId") Long tenantId) {
        List<HolidayResponse> holidays = holidayService.getAllHolidays(tenantId);
        return ResponseEntity.ok(holidays);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HolidayResponse> getHolidayById(@PathVariable("tenantId") Long tenantId,
                                                          @PathVariable Long id) {
        HolidayResponse holiday = holidayService.getHolidayById(tenantId, id);
        return ResponseEntity.ok(holiday);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HolidayResponse> updateHoliday(@PathVariable("tenantId") Long tenantId,
                                                         @PathVariable Long id,
                                                         @RequestBody @Valid HolidayRequest holidayRequest) {
        HolidayResponse updatedHoliday = holidayService.updateHoliday(tenantId, id, holidayRequest);
        return ResponseEntity.ok(updatedHoliday);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHoliday(@PathVariable("tenantId") Long tenantId,
                                              @PathVariable Long id) {
        holidayService.deleteHoliday(tenantId, id);
       return ResponseEntity.ok("Holiday deleted successfully!");
    }
}
