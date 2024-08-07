package com.insa.dto.responseDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentTypeResponse {
    private Long id;
    private String departmentTypeName;
    @NotBlank(message = "description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;
    private Long tenantId;
}
