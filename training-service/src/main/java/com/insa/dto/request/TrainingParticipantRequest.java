package com.insa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingParticipantRequest {

    private String SearchBy;
    private String participantEmployeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String reason;
}
