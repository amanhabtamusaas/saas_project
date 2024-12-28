package com.hr_planning_service.controller;

import com.hr_planning_service.config.PermissionEvaluator;
import com.hr_planning_service.dto.request.NeedRequestRequestDto;
import com.hr_planning_service.dto.response.NeedRequestResponseDto;
import com.hr_planning_service.service.NeedRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/need-requests/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Need Request")
public class NeedRequestController {

    private final NeedRequestService needRequestService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/create-hr-need")
    public ResponseEntity<?> createNeedRequest(
            @PathVariable("tenantId") UUID tenantId,
            @Valid @RequestBody NeedRequestRequestDto needRequestRequest) {

        permissionEvaluator.addHrNeedRequestPermission();

        NeedRequestResponseDto createdNeedRequest = needRequestService
                .createNeedRequest(tenantId, needRequestRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNeedRequest);
    }

    @GetMapping("/get-all-hr-need")
    public ResponseEntity<?> getAllNeedRequests(
            @PathVariable("tenantId") UUID tenantId) {

        permissionEvaluator.getAllHrNeedRequestsPermission();

        List<NeedRequestResponseDto> needRequests = needRequestService
                .getAllNeedRequests(tenantId);
        return ResponseEntity.ok(needRequests);
    }

    @GetMapping("/get-hr-need-by/{id}")
    public ResponseEntity<?> getNeedRequestById(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.getHrNeedRequestByIdPermission();

        NeedRequestResponseDto needRequest = needRequestService
                .getNeedRequestById(tenantId, id);
        return ResponseEntity.ok(needRequest);
    }

    @PutMapping("/update-hr-need/{id}")
    public ResponseEntity<?> updateNeedRequest(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id,
            @Valid @RequestBody NeedRequestRequestDto needRequestRequest) {

        permissionEvaluator.updateHrNeedRequestPermission();

        NeedRequestResponseDto updatedNeedRequest = needRequestService
                .updateNeedRequest(tenantId, id, needRequestRequest);
        return ResponseEntity.ok(updatedNeedRequest);
    }

    @DeleteMapping("/remove-hr-need/{id}")
    public ResponseEntity<?> deleteNeedRequest(
            @PathVariable("tenantId") UUID tenantId,
            @PathVariable("id") UUID id) {

        permissionEvaluator.deleteHrNeedRequestPermission();

        needRequestService.deleteNeedRequest(tenantId, id);
        return ResponseEntity.ok("HR need deleted successfully!");
    }
}