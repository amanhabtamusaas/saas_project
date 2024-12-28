package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.TitleNameRequest;
import com.employee_service.dto.response.TitleNameResponse;
import com.employee_service.service.TitleNameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/title-names/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Title Name")
public class TitleNameController {

    private final TitleNameService titleNameService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addTitleName(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody TitleNameRequest request) {

        permissionEvaluator.addTitleNamePermission();

        TitleNameResponse response = titleNameService
                .addTitleName(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTitleNames(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllTitleNamesPermission();

        List<TitleNameResponse> responses = titleNameService
                .getAllTitleNames(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{title-id}")
    public ResponseEntity<?> getTitleNameById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("title-id") UUID titleId) {

        permissionEvaluator.getTitleNameByIdPermission();

        TitleNameResponse response = titleNameService
                .getTitleNameById(tenantId, titleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{title-id}")
    public ResponseEntity<?> updateTitleName(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("title-id") UUID titleId,
            @RequestBody TitleNameRequest request) {

        permissionEvaluator.updateTitleNamePermission();

        TitleNameResponse response = titleNameService
                .updateTitleName(tenantId, titleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{title-id}")
    public ResponseEntity<?> deleteTitleName(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("title-id") UUID titleId) {

        permissionEvaluator.deleteTitleNamePermission();

        titleNameService.deleteTitleName(tenantId, titleId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Title name deleted successfully!");
    }
}