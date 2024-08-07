package com.insa.controller;

import com.insa.dto.request.ExperienceRequest;
import com.insa.dto.response.ExperienceResponse;
import com.insa.service.ExperienceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/applicant-experiences/{tenant-id}/{applicant-id}")
@RequiredArgsConstructor
@Tag(name = "Applicant Experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping("/add")
    public ResponseEntity<?> addExperience(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @RequestPart("experience") ExperienceRequest experienceRequest,
            @RequestPart("document") MultipartFile file) {

        try {
            ExperienceResponse experience = experienceService
                    .addExperience (tenantId, applicantId, experienceRequest, file);
            return new ResponseEntity<> (experience, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the experience: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllExperiences(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId) {

        try {
            List<ExperienceResponse> experiences = experienceService
                    .getAllExperiences (tenantId, applicantId);
            return ResponseEntity.ok (experiences);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the experiences: " + e.getMessage());
        }
    }

    @GetMapping("/get/{experience-id}")
    public ResponseEntity<?> getExperienceById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("experience-id") Long experienceId) {

        try {
            ExperienceResponse experience = experienceService
                    .getExperienceById (tenantId, applicantId, experienceId);
            return ResponseEntity.ok (experience);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the experience: " + e.getMessage());
        }
    }

    @GetMapping(value = "/download-document/{experience-id}")
    public ResponseEntity<?> getExperienceDocumentById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("experience-id") Long experienceId) {

        try {
            ExperienceResponse experience = experienceService
                    .getExperienceById (tenantId, applicantId, experienceId);
            if (experience == null || experience.getFileType() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Applicant experience document not found");
            }
            MediaType mediaType = MediaType.valueOf (experience.getFileType ());
            byte[] documentBytes = experienceService
                    .getExperienceDocumentById (tenantId, applicantId, experienceId, mediaType);
            return ResponseEntity.ok().contentType(mediaType).body(documentBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while downloading the document: " + e.getMessage());
        }
    }

    @PutMapping("/update/{experience-id}")
    public ResponseEntity<?> updateExperience(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("experience-id") Long experienceId,
            @RequestPart("experience") ExperienceRequest request,
            @RequestPart(value = "document", required = false) MultipartFile file) {

        try {
            ExperienceResponse experience = experienceService
                    .updateExperience (tenantId, applicantId, experienceId, request, file);
            return ResponseEntity.ok (experience);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the experience: " + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{experience-id}")
    public ResponseEntity<?> deleteExperience(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("applicant-id") Long applicantId,
            @PathVariable("experience-id") Long experienceId) {

        try {
            experienceService.deleteExperience (tenantId, applicantId, experienceId);
            return ResponseEntity.ok ("Experience deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the experience: " + e.getMessage());
        }
    }
}
