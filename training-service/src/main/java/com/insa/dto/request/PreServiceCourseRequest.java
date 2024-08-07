package com.insa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceCourseRequest {

    private Long courseTypeId;
    private String courseName;
    private String description;
}
