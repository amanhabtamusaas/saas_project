package com.organization_service.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {
    private String departmentName;
    private LocalDate establishedDate;
    private UUID departmentTypeId;
  //  private UUID parentDepartmentId;
//    private UUID tenantId;
//    private List<UUID> subDepartmentIds;

}
