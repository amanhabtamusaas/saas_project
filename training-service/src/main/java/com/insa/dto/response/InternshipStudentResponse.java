package com.insa.dto.response;

import com.insa.enums.Semester;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipStudentResponse extends BaseResponse{

    private Long budgetYearId;
    private String semester;
    private Long universityId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstName;
    private String middleName;
    private String lastName;
    private String IdNumber;
    private String phoneNumber;
    private String stream;
    private Long locationId;
    private Long placedDepartmentId;
    private String internshipStatus;
    private String remark;
}
