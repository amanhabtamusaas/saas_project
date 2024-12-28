package com.organization_service.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantRequest {
    private String tenantName;
    private String abbreviatedName;
    private String logo;
    private LocalDate establishedYear;
    private String mission;
    private String vision;
}
