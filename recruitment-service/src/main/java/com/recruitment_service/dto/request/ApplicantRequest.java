package com.recruitment_service.dto.request;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantRequest {
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

    @NotNull(message = "Marital status cannot be null")
    private String maritalStatus;

    @NotBlank(message = "Nationality cannot be blank")
    private String nationality;

    @NotNull(message = "Location cannot be null")
    private UUID locationId;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    private String email;

    private String officeTelephone;
    private String homeTelephone;
    private String houseNumber;
    private String poBox;
    private String skills;
    private String otherInformation;
    private String hobbies;

    /*private String profileImageName;
    private String profileImageType;
    @Lob
    @Column(length = 50000000)
    private byte[] profileImageBytes;*/

    private UUID recruitmentId;
}
