package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.LanguageNameRequest;
import com.employee_service.dto.response.LanguageNameResponse;
import com.employee_service.service.LanguageNameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/language-names/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Language Name")
public class LanguageNameController {

    private final LanguageNameService languageNameService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addLanguageName(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody LanguageNameRequest request) {

        permissionEvaluator.addLanguageNamePermission();

        LanguageNameResponse response = languageNameService
                .addLanguageName(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLanguageNames(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllLanguageNamesPermission();

        List<LanguageNameResponse> responses = languageNameService
                .getAllLanguageNames(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{language-id}")
    public ResponseEntity<?> getLanguageNameById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.getLanguageNameByIdPermission();

        LanguageNameResponse response = languageNameService
                .getLanguageNameById(tenantId, languageId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{language-id}")
    public ResponseEntity<?> updateLanguageName(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("language-id") UUID languageId,
            @RequestBody LanguageNameRequest request) {

        permissionEvaluator.updateLanguageNamePermission();

        LanguageNameResponse response = languageNameService
                .updateLanguageName(tenantId, languageId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{language-id}")
    public ResponseEntity<?> deleteLanguageName(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.deleteLanguageNamePermission();

        languageNameService.deleteLanguageName(tenantId, languageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Language name deleted successfully!");
    }
}