package com.leave_service.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetYearResponse {
    private UUID id;
//    private int year;
    private String budgetYear;

    private boolean isActive;
    private String description;

    private UUID tenantId;
//    @CreatedDate
//    @Column(name = "created_date", nullable = false, updatable = false)
//    private LocalDate createdDate;
//    @LastModifiedDate
//    @Column(name = "last_modified_date")
//    private LocalDate lastModifiedDate;
//    @CreatedBy
//    @Column(name = "created_by", nullable = false, updatable = false)
//    private String createdBy;
//    @LastModifiedBy
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;
}
