package com.insa.controller;

import com.insa.dto.request.CheckedDocumentRequest;
import com.insa.dto.response.CheckedDocumentResponse;
import com.insa.dto.response.PreServiceTraineeResponse;
import com.insa.service.CheckedDocumentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Trainee Checked Document")
public class CheckedDocumentController {

    private final CheckedDocumentService checkedDocumentService;

    @PostMapping("/add")
    public ResponseEntity<?> addCheckedDocument(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody CheckedDocumentRequest request) {

        try {
            CheckedDocumentResponse response = checkedDocumentService
                    .addCheckedDocument(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the document: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCheckedDocuments(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<CheckedDocumentResponse> responses = checkedDocumentService
                    .getAllCheckedDocuments(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the documents: " + e.getMessage());
        }
    }

    @GetMapping("/get/{document-id}")
    public ResponseEntity<?> getCheckedDocument(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("document-id") Long documentId) {

        try {
            CheckedDocumentResponse response = checkedDocumentService
                    .getCheckedDocument(tenantId, documentId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the document: " + e.getMessage());
        }
    }

    @GetMapping("/get/trainee-documents/{trainee-id}")
    public ResponseEntity<?> getCheckedDocumentsByTrainee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId) {

        try {
            List<CheckedDocumentResponse> responses = checkedDocumentService
                    .getCheckedDocumentByTrainee(tenantId, traineeId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the documents: " + e.getMessage());
        }
    }

    @PutMapping("/update/{document-id}")
    public ResponseEntity<?> updateCheckedDocument(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("document-id") Long documentId,
            @RequestBody CheckedDocumentRequest request) {

        try {
            CheckedDocumentResponse response = checkedDocumentService
                    .updateCheckedDocument(tenantId, documentId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the document: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove-trainee-document/{trainee-id}/{document-id}")
    public ResponseEntity<?> removeCheckedDocumentFromTrainee(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("trainee-id") Long traineeId,
            @PathVariable("document-id") Long documentId) {

        try {
            checkedDocumentService.removeCheckedDocumentFromTrainee(tenantId, traineeId, documentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Document removed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the document: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{document-id}")
    public ResponseEntity<?> deleteCheckedDocument(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("document-id") Long documentId) {

        try {
            checkedDocumentService.deleteCheckedDocument(tenantId, documentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Document deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the document: " + e.getMessage());
        }
    }
}
