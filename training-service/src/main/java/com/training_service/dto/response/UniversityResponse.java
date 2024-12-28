package com.training_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityResponse extends BaseResponse {

    private String universityName;
    private String abbreviatedName;
    private UUID locationId;
    private Double costPerPerson;
    private String mobilePhoneNumber;
    private String telephoneNumber;
    private String email;
    private String fax;
    private String website;
    private String remark;
}
