package com.employee_service.controller;

import com.employee_service.config.PermissionEvaluator;
import com.employee_service.dto.request.LanguageRequest;
import com.employee_service.dto.response.LanguageResponse;
import com.employee_service.service.LanguageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/languages/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Language")
public class LanguageController {

    private final LanguageService languageService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @RequestBody LanguageRequest languageRequest) {

        permissionEvaluator.addLanguagePermission();

        LanguageResponse language = languageService
                .addLanguage(tenantId, employeeId, languageRequest);
        return new ResponseEntity<>(language, HttpStatus.CREATED);
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllLanguages(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId) {

        permissionEvaluator.getAllLanguagesPermission();

        List<LanguageResponse> languages = languageService
                .getAllLanguages(tenantId, employeeId);
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/get/employee-languages")
    public ResponseEntity<?> getEmployeeLanguages(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestParam("employee-id") String employeeId) {

        permissionEvaluator.getLanguagesByEmployeeIdPermission();

        List<LanguageResponse> languages = languageService
                .getEmployeeLanguages(tenantId, employeeId);
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/{employee-id}/get/{language-id}")
    public ResponseEntity<?> getLanguageById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.getLanguageByIdPermission();

        LanguageResponse language = languageService
                .getLanguageById(tenantId, employeeId, languageId);
        return ResponseEntity.ok(language);
    }

    @PutMapping("/{employee-id}/update/{language-id}")
    public ResponseEntity<?> updateLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("language-id") UUID languageId,
            @RequestBody LanguageRequest languageRequest) {

        permissionEvaluator.updateLanguagePermission();

        LanguageResponse language = languageService
                .updateLanguage(tenantId, employeeId, languageId, languageRequest);
        return ResponseEntity.ok(language);
    }

    @DeleteMapping("/{employee-id}/delete/{language-id}")
    public ResponseEntity<?> deleteLanguage(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("employee-id") UUID employeeId,
            @PathVariable("language-id") UUID languageId) {

        permissionEvaluator.deleteLanguagePermission();

        languageService.deleteLanguage(tenantId, employeeId, languageId);
        return ResponseEntity.ok("Language deleted successfully");
    }
}