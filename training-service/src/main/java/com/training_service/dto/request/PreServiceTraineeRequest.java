package com.training_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceTraineeRequest {

    @NotBlank(message = "Trainee id cannot be blank")
    private String traineeId;

    @NotNull(message = "Budget year id cannot be null")
    private UUID budgetYearId;

    @NotBlank(message = "Batch code cannot be blank")
    private String batchCode;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Amharic first name cannot be blank")
    private String amharicFirstName;

    @NotBlank(message = "Amharic middle name cannot be blank")
    private String amharicMiddleName;

    @NotBlank(message = "Amharic last name cannot be blank")
    private String amharicLastName;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @NotNull(message = "Location id cannot be null")
    private UUID locationId;

    private String telephoneNumber;

    @NotBlank(message = "Mobile number cannot be blank")
    private String mobileNumber;

    @NotNull(message = "Education level id cannot be null")
    private UUID educationLevelId;

    @NotNull(message = "Field of study id cannot be null")
    private UUID fieldOfStudyId;

    private String remark;

    private Set<UUID> documentIds = new HashSet<>();
}
