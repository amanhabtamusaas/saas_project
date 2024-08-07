package com.insa.dto.responseDto;

import com.insa.dto.requestDto.DepartmentRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponse {
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
