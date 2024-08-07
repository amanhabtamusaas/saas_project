package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse extends BaseResponse{
    private String employeeId;
    private Long titleNameId;
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
}


