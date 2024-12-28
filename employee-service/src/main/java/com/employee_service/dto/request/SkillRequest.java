package com.employee_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillRequest {

    @NotBlank(message = "Skill type cannot be blank")
    private String skillType;

    @NotBlank(message = "Skill level cannot be blank")
    private String skillLevel;

    private String description;
}
