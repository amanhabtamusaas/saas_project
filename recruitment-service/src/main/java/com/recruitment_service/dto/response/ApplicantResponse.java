package com.recruitment_service.dto.response;

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
public class ApplicantResponse extends BaseResponse {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String maritalStatus;
    private String nationality;
    private UUID locationId;
    private String phoneNumber;
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
    private byte[] profileImageBytes;*/
    private UUID recruitmentId;
}
