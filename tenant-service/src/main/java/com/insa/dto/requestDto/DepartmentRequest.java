package com.insa.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {
    private String departmentName;
    private LocalDate establishedDate;
    private Long departmentTypeId;
  //  private Long parentDepartmentId;
//    private Long tenantId;
//    private List<Long> subDepartmentIds;

}
