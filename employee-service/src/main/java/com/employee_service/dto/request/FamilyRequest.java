package com.employee_service.dto.request;

import com.employee_service.enums.Gender;
import com.employee_service.enums.RelationshipType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRequest {

    @NotNull(message = "First name cannot be null")
    private String relationshipType;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    private String houseNumber;
    private String homeTelephone;
    private String officeTelephone;

    @Pattern(regexp = "\\d{10}", message = "Invalid mobile number")
    private String mobileNumber;

    @Email(message = "Invalid email address")
    private String email;

    private String poBox;

    @NotNull(message = "Emergency contact flag cannot be null")
    private boolean isEmergencyContact;
}
