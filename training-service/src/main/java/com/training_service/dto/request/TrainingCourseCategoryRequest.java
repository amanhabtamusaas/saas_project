package com.training_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingCourseCategoryRequest {

    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;

    private String description;
}
