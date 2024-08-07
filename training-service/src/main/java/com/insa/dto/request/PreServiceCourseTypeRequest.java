package com.insa.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceCourseTypeRequest {

    @NotBlank(message = "Course type cannot be blank")
    private String courseType;

    private String description;
}
