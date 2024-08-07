package com.insa.controller;

import com.insa.dto.request.LanguageRequest;
import com.insa.dto.response.LanguageResponse;
import com.insa.service.LanguageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Language")
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/{employee-id}/add")
    public ResponseEntity<?> addLanguage(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @RequestBody LanguageRequest languageRequest) {

        try {
            LanguageResponse language = languageService
                    .addLanguage(tenantId, employeeId, languageRequest);
            return new ResponseEntity<>(language, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the language: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllLanguages(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            List<LanguageResponse> languages = languageService
                    .getAllLanguages(tenantId, employeeId);
            return ResponseEntity.ok(languages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the languages: " + e.getMessage());
        }
    }

    @GetMapping("/get/employee-languages")
    public ResponseEntity<?> getEmployeeLanguages(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId) {

        try {
            List<LanguageResponse> languages = languageService
                    .getEmployeeLanguages(tenantId, employeeId);
            return ResponseEntity.ok(languages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the languages: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get/{language-id}")
    public ResponseEntity<?> getLanguageById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("language-id") Long languageId) {


        try {
            LanguageResponse language = languageService
                .getLanguageById (tenantId, employeeId, languageId);
            return ResponseEntity.ok (language);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the language: " + e.getMessage());
        }
    }

    @PutMapping("/{employee-id}/update/{language-id}")
    public ResponseEntity<?> updateLanguage(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("language-id") Long languageId,
            @RequestBody LanguageRequest languageRequest) {

        try {
            LanguageResponse language = languageService
                    .updateLanguage(tenantId, employeeId, languageId, languageRequest);
            return ResponseEntity.ok(language);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the language: " + e.getMessage());
        }
    }

    @DeleteMapping("/{employee-id}/delete/{language-id}")
    public ResponseEntity<?> deleteLanguage(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("language-id") Long languageId) {

        try {
            languageService.deleteLanguage (tenantId, employeeId, languageId);
            return ResponseEntity.ok ("Language deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the language: " + e.getMessage());
        }
    }
}
