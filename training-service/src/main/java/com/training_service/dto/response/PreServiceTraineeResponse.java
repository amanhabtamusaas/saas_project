package com.training_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceTraineeResponse extends BaseResponse {

    private String traineeId;
    private UUID budgetYearId;
    private String batchCode;
    private String firstName;
    private String middleName;
    private String lastName;
    private String amharicFirstName;
    private String amharicMiddleName;
    private String amharicLastName;
    private String gender;
    private UUID locationId;
    private String telephoneNumber;
    private String mobileNumber;
    private UUID educationLevelId;
    private UUID fieldOfStudyId;
    private String remark;
    private String imageName;
    private String imageType;
    private byte[] image;
    private List<CheckedDocumentResponse> checkedDocuments;
}
