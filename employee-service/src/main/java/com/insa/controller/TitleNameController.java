package com.insa.controller;

import com.insa.dto.request.TitleNameRequest;
import com.insa.dto.response.TitleNameResponse;
import com.insa.service.TitleNameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/title-names/{tenant-id}")
@RequiredArgsConstructor
@Tag(name = "Title Name")
public class TitleNameController {

    private final TitleNameService titleNameService;

    @PostMapping("/add")
    public ResponseEntity<?> addTitleName(
            @PathVariable("tenant-id") Long tenantId,
            @RequestBody TitleNameRequest request) {

        try {
            TitleNameResponse response = titleNameService
                    .addTitleName(tenantId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the title name: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllTitleNames(
            @PathVariable("tenant-id") Long tenantId) {

        try {
            List<TitleNameResponse> responses = titleNameService
                    .getAllTitleNames(tenantId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the title names: " + e.getMessage());
        }
    }

    @GetMapping("/get/{title-id}")
    public ResponseEntity<?> getTitleName(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("title-id") Long titleId) {

        try {
            TitleNameResponse response = titleNameService
                    .getTitleName(tenantId, titleId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the title name: " + e.getMessage());
        }
    }

    @PutMapping("/update/{title-id}")
    public ResponseEntity<?> updateTitleName(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("title-id") Long titleId,
            @RequestBody TitleNameRequest request) {

        try {
            TitleNameResponse response = titleNameService
                    .updateTitleName(tenantId, titleId, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the title name: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{title-id}")
    public ResponseEntity<?> deleteTitleName(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("title-id") Long titleId) {

        try {
            titleNameService.deleteTitleName(tenantId, titleId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Title name deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the title name: " + e.getMessage());
        }
    }
}
