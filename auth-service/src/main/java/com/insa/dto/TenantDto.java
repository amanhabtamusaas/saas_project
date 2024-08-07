package com.insa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantDto {
    private Long id;
    private String tenantName;
    private String abbreviatedName;
    private LocalDate establishedYear;
    private String mission;
    private String vision;
    private String logoName;
    private String logoType;
    private byte[] logoBytes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
