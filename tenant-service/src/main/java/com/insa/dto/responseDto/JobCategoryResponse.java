package com.insa.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobCategoryResponse {
    private Long id;
    private String jobCategoryName;
    private String description;
    private Long tenantId;
}
