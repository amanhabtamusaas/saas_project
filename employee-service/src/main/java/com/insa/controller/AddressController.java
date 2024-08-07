package com.insa.controller;

import com.insa.dto.request.AddressRequest;
import com.insa.dto.response.AddressResponse;
import com.insa.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses/{tenant-id}")
@RequiredArgsConstructor
@Tag (name = "Address")
//@SecurityRequirement(name = "Keycloak") // used for swagger
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{employee-id}/add")
    //@Hidden
    public ResponseEntity<?> addAddress(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @RequestBody AddressRequest addressRequest) {

        try {
            AddressResponse address = addressService
                    .addAddress (tenantId, employeeId, addressRequest);
            return new ResponseEntity<> (address, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while adding the address: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get-all")
    public ResponseEntity<?> getAllAddresses(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId) {

        try {
            List<AddressResponse> addresses = addressService
                    .getAllAddresses(tenantId, employeeId);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the addresses: " + e.getMessage());
        }
    }

    @GetMapping("/get/employee-addresses")
    public ResponseEntity<?> getEmployeeAddresses(
            @PathVariable("tenant-id") Long tenantId,
            @RequestParam("employee-id") String employeeId) {

        try {
            List<AddressResponse> addresses = addressService
                    .getEmployeeAddresses(tenantId, employeeId);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the addresses: " + e.getMessage());
        }
    }

    @GetMapping("/{employee-id}/get/{address-id}")
    public ResponseEntity<?> getAddressById(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("address-id") Long addressId) {

        try {
            AddressResponse address = addressService
                    .getAddressById(tenantId, employeeId, addressId);
            return ResponseEntity.ok(address);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the address: " + e.getMessage());
        }
    }

    @PutMapping("/{employee-id}/update/{address-id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("address-id") Long addressId,
            @RequestBody AddressRequest addressRequest) {

        try {
            AddressResponse address = addressService
                    .updateAddress(tenantId, employeeId, addressId, addressRequest);
            return ResponseEntity.ok(address);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the address: " + e.getMessage());
        }
    }

    @DeleteMapping("/{employee-id}/delete/{address-id}")
    public ResponseEntity<?> deleteAddress(
            @PathVariable("tenant-id") Long tenantId,
            @PathVariable("employee-id") Long employeeId,
            @PathVariable("address-id") Long addressId) {

        try {
            addressService.deleteAddress(tenantId, employeeId, addressId);
            return ResponseEntity.ok("Address Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the address: " + e.getMessage());
        }
    }
}
