package com.insa.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "Employee ID cannot be blank")
    private String employeeId;

    @NotNull(message = "Title name id cannot be null")
    private Long titleNameId;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Marital status cannot be null")
    private String maritalStatus;

    @NotNull(message = "Employment type cannot be null")
    private String employmentType;

    @NotNull(message = "Hired date cannot be null")
    @PastOrPresent(message = "Joining date must be in the past or present")
    private LocalDate hiredDate;

    @FutureOrPresent(message = "Exit date must be in the future or present")
    private LocalDate endDate;

    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;

    @NotNull(message = "Job Id cannot be null")
    private Long jobId;

    private String dutyStation;
    private String nationality;
    private String fydaNumber;
    private String passportNumber;
    private String tinNumber;
    private String pensionNumber;
}


