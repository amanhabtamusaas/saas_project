package com.insa.controller;

import com.insa.dto.request.LanguageNameRequest;
import com.insa.dto.response.LanguageNameResponse;
import com.insa.service.LanguageNameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/language-names/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Language Name")
public class LanguageNameController {

    private final LanguageNameService languageNameService;

    @PostMapping("/add")
    public ResponseEntity<?> addLanguageName(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody LanguageNameRequest request) {

        try {
            LanguageNameResponse response = languageNameService
                    .addLanguageName(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the language name: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLanguageNames(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<LanguageNameResponse> responses = languageNameService
                    .getAllLanguageNames(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the language names: " + e.getMessage());
        }
    }

    @GetMapping("/get/{language-id}")
    public ResponseEntity<?> getLanguageName(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("language-id") Long languageId) {

        try {
            LanguageNameResponse response = languageNameService
                    .getLanguageName(tenantId, languageId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the language name: " + e.getMessage());
        }
    }

    @PutMapping("/update/{language-id}")
    public ResponseEntity<?> updateLanguageName(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("language-id") Long languageId,
            @RequestBody LanguageNameRequest request) {

        try {
            LanguageNameResponse response = languageNameService
                    .updateLanguageName(tenantId, languageId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the language name: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{language-id}")
    public ResponseEntity<?> deleteLanguageName(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("language-id") Long languageId) {

        try {
            languageNameService.deleteLanguageName(tenantId, languageId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Language name deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the language name: " + e.getMessage());
        }
    }
}
