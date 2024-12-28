package com.organization_service.dto.requestDto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class JobGradeRequestDto {

    @NotBlank(message = "Job grade name cannot be blank")
    private String jobGradeName;

    @NotBlank(message = "Description cannot be blank")
    private String description;

  //  private UUID tenantId;



}

