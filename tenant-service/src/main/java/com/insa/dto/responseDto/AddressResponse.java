package com.insa.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Long id;
    private String blockNo;
    private String floor;
    private String officeNumber;
    private String officeTelephone;
    private String mobileNumber;
    private String email;
    private String website;
    private String poBox;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long tenantId;
    private Long locationId;
    private Long departmentId;
}
