package com.training_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceCourseRequest {

    private UUID courseTypeId;
    private String courseName;
    private String description;
}
