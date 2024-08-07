package com.insa.leave_service.dto;

import com.insa.leave_service.enums.Gender;
import com.insa.leave_service.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private Long tenantId;
    private Long employeeId;
    private Long departmentId;
    private String titleName;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    private MaritalStatus maritalStatus;
    private String employmentType;
    private Double salary;
    private Long jobRegistrationId;
    private Long payGradeId;
    private String dutyStation;
    private String fydaNumber;
    private String passportNumber;
    private String tinNumber;
    private String pensionNumber;
    private String religion;
    private LocalDate joiningDate;
    private LocalDate exitDate;
    private String profileImageName;
    private String profileImageType;
    private byte[] profileImageBytes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}