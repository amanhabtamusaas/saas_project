package com.training_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipPlacementRequest {

    @NotNull(message = "Placed department Id cannot be null")
    private UUID placedDepartmentId;

    private String remark;
}
