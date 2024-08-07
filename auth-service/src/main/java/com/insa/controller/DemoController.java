package com.insa.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demos")
@RequiredArgsConstructor
@Tag(name = "Demo")
//@SecurityRequirement(name = "Keycloak")
public class DemoController {

    @PostMapping("/add")
    public ResponseEntity<?> createDemo() {
        try {
            String response = "Add Endpoint";
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something is wrong while adding demo " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDemos() {
        try {
            String response = "Get All Endpoint";
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something is wrong while fetching demos " + e.getMessage());
        }
    }
}
