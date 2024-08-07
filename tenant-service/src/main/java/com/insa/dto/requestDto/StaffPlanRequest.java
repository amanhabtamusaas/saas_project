package com.insa.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Data

public class StaffPlanRequest {

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
    private Long jobRegistrationId;
    private Long departmentId;

}

