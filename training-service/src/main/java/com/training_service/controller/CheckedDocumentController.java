package com.training_service.controller;

import com.training_service.config.PermissionEvaluator;
import com.training_service.dto.request.CheckedDocumentRequest;
import com.training_service.dto.response.CheckedDocumentResponse;
import com.training_service.service.CheckedDocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Trainee Checked Document")
public class CheckedDocumentController {

    private final CheckedDocumentService checkedDocumentService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> addCheckedDocument(
            @PathVariable("tenant-id") UUID tenantId,
            @RequestBody CheckedDocumentRequest request) {

        permissionEvaluator.addPreServiceCheckedDocumentPermission();

        CheckedDocumentResponse response = checkedDocumentService
                .addCheckedDocument(tenantId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCheckedDocuments(
            @PathVariable("tenant-id") UUID tenantId) {

        permissionEvaluator.getAllPreServiceCheckedDocumentsPermission();

        List<CheckedDocumentResponse> responses = checkedDocumentService
                .getAllCheckedDocuments(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/get/{document-id}")
    public ResponseEntity<?> getCheckedDocumentById(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("document-id") UUID documentId) {

        permissionEvaluator.getPreServiceCheckedDocumentByIdPermission();

        CheckedDocumentResponse response = checkedDocumentService
                .getCheckedDocumentById(tenantId, documentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/trainee-documents/{trainee-id}")
    public ResponseEntity<?> getCheckedDocumentsByTraineeId(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId) {

        permissionEvaluator.getPreServiceCheckedDocumentsByTraineeIdPermission();

        List<CheckedDocumentResponse> responses = checkedDocumentService
                .getCheckedDocumentByTraineeId(tenantId, traineeId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/update/{document-id}")
    public ResponseEntity<?> updateCheckedDocument(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("document-id") UUID documentId,
            @RequestBody CheckedDocumentRequest request) {

        permissionEvaluator.updatePreServiceCheckedDocumentPermission();

        CheckedDocumentResponse response = checkedDocumentService
                .updateCheckedDocument(tenantId, documentId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove-trainee-document/{trainee-id}/{document-id}")
    public ResponseEntity<?> removeCheckedDocumentFromTrainee(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("trainee-id") UUID traineeId,
            @PathVariable("document-id") UUID documentId) {

        permissionEvaluator.removeTraineeCheckedDocumentPermission();

        checkedDocumentService.removeCheckedDocumentFromTrainee(tenantId, traineeId, documentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Document removed successfully!");
    }

    @DeleteMapping("/delete/{document-id}")
    public ResponseEntity<?> deleteCheckedDocument(
            @PathVariable("tenant-id") UUID tenantId,
            @PathVariable("document-id") UUID documentId) {

        permissionEvaluator.deletePreServiceCheckedDocumentPermission();

        checkedDocumentService.deleteCheckedDocument(tenantId, documentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Document deleted successfully!");
    }
}