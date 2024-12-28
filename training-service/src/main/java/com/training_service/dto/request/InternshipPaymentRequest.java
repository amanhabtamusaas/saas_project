package com.training_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipPaymentRequest {

    @NotBlank(message = "Reference letter cannot be blank")
    private String referenceLetter;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotNull(message = "Payment amount cannot be null")
    @Min(value = 0, message = "Payment amount must be non-negative")
    private Double paymentAmount;

    private String remark;

    @NotNull(message = "Intern id cannot be null")
    private UUID internId;
}
