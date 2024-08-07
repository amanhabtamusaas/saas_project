package com.insa.dto.apiDto;

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
    private String employeeId;
    private String titleName;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private String employmentType;
    private Long departmentId;
    private Long jobId;
    private String dutyStation;
    private String nationality;
    private String fydaNumber;
    private String passportNumber;
    private String tinNumber;
    private String pensionNumber;
    private LocalDate hiredDate;
    private LocalDate endDate;
    private String profileImageName;
    private String profileImageType;
    private byte[] profileImageBytes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}


