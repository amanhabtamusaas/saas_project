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
public class UniversityRequest {

    @NotBlank(message = "university name cannot be blank")
    private String universityName;

    @NotBlank(message = "Abbreviated name cannot be blank")
    private String abbreviatedName;

    @NotNull(message = "Location id cannot be null")
    private UUID locationId;

    @NotNull(message = "Cost per person cannot be null")
    private Double costPerPerson;

    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Invalid mobile phone number")
    private String mobilePhoneNumber;

    @NotNull(message = "Telephone number cannot be null")
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Invalid telephone number")
    private String telephoneNumber;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Invalid fax number")
    private String fax;

    private String website;
    private String remark;
}
