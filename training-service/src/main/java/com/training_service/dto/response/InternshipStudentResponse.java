package com.training_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipStudentResponse extends BaseResponse{

    private UUID budgetYearId;
    private String semester;
    private UUID universityId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String firstName;
    private String middleName;
    private String lastName;
    private String IdNumber;
    private String phoneNumber;
    private String stream;
    private UUID locationId;
    private UUID placedDepartmentId;
    private String internshipStatus;
    private String remark;
}
