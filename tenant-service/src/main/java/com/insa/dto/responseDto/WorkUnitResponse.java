package com.insa.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkUnitResponse {
    private Long id;
    private String workUnitName;
    private String description;
    private Long tenantId;

}
