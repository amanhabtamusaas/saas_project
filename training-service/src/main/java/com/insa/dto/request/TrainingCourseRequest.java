package com.insa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingCourseRequest {

    @NotBlank(message = "Course name cannot be blank")
    private String courseName;

    private String description;

    @NotNull(message = "Course category id cannot be null")
    private Long courseCategoryId;
}
