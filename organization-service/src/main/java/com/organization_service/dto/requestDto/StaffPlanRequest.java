package com.organization_service.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Data

public class StaffPlanRequest {

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
    private UUID jobRegistrationId;
    private UUID departmentId;

}

