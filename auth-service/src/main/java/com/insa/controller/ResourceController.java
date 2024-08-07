package com.insa.controller;

import com.insa.dto.request.ResourceRequest;
import com.insa.dto.response.ResourceResponse;
import com.insa.service.ResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keycloak/resources")
@RequiredArgsConstructor
@Tag(name = "Resource")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping("/add")
    public ResponseEntity<?> createResource(@RequestBody ResourceRequest request) {

        try {
            ResourceResponse response = resourceService.addResource(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the resource: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllResources() {

        try {
            List<ResourceResponse> response = resourceService.getAllResources();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the resources: " + e.getMessage());
        }
    }

    @GetMapping("/get/{resource-name}")
    public ResponseEntity<?> getResourceByName(@PathVariable("resource-name") String resourceName) {

        try {
            ResourceRepresentation response = resourceService.getAuthorizationResource(resourceName);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the resource: " + e.getMessage());
        }
    }
//
//    @PutMapping("/update/{resource-name}")
//    public ResponseEntity<?> updateResource(@PathVariable("resource-name") String resourceName,
//                                        @RequestBody ResourceRequest request) {
//
//        try {
//            ResourceResponse response = resourceService.updateResource(resourceName, request);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while updating the resource: " + e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/delete/{resource-name}")
//    public ResponseEntity<?> deleteResource(@PathVariable("resource-name") String resourceName) {
//
//        try {
//            resourceService.deleteResource(resourceName);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body("Resource deleted successfully!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while deleting the resource: " + e.getMessage());
//        }
//    }
}
