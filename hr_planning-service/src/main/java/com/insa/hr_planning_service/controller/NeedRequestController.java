package com.insa.hr_planning_service.controller;

import com.insa.hr_planning_service.dto.request.NeedRequestRequestDto;
import com.insa.hr_planning_service.dto.response.NeedRequestResponseDto;
import com.insa.hr_planning_service.service.NeedRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/{tenantId}/need-requests")
@RequiredArgsConstructor
@Tag(name = "Need Request")
public class NeedRequestController {

    private final NeedRequestService needRequestService;
    @PostMapping("/create-hr-need")
    public ResponseEntity<NeedRequestResponseDto> createNeedRequest(
            @PathVariable("tenantId") Long tenantId,
            @Valid @RequestBody NeedRequestRequestDto needRequestRequest) {
        NeedRequestResponseDto createdNeedRequest = needRequestService.createNeedRequest(tenantId, needRequestRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNeedRequest);
    }

    @GetMapping("/get-all-hr-need")
    public ResponseEntity<List<NeedRequestResponseDto>> getAllNeedRequests(
            @PathVariable("tenantId") Long tenantId) {
        List<NeedRequestResponseDto> needRequests = needRequestService.getAllNeedRequests(tenantId);
        return ResponseEntity.ok(needRequests);
    }

    @GetMapping("/get-hr-need-by/{id}")
    public ResponseEntity<NeedRequestResponseDto> getNeedRequestById(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id) {
        NeedRequestResponseDto needRequest = needRequestService.getNeedRequestById(tenantId, id);
        return ResponseEntity.ok(needRequest);
    }

    @PutMapping("/update-hr-need/{id}")
    public ResponseEntity<NeedRequestResponseDto> updateNeedRequest(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id,
            @Valid @RequestBody NeedRequestRequestDto needRequestRequest) {
        NeedRequestResponseDto updatedNeedRequest = needRequestService.updateNeedRequest(tenantId, id, needRequestRequest);
        return ResponseEntity.ok(updatedNeedRequest);
    }

    @DeleteMapping("/remove-hr-need/{id}")
    public ResponseEntity<String> deleteNeedRequest(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("id") Long id) {
        needRequestService.deleteNeedRequest(tenantId, id);
        return ResponseEntity.ok("Hr need deleted successfully!");
    }
}
