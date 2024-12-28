package com.training_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingInstitutionRequest {

    @NotBlank(message = "Institution name cannot be blank")
    private String institutionName;

    @NotNull(message = "Location id cannot be null")
    private UUID locationId;

    @NotNull(message = "Cost per person cannot be null")
    private Double costPerPerson;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Invalid phone number")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Invalid telephone number")
    private String fax;

    private String website;
    private String tinNumber;
    private String remark;
}
