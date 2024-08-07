package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingParticipantResponse extends BaseResponse{

    private Long participantEmployeeId;
    private Long trainingId;
    private String reason;
}
