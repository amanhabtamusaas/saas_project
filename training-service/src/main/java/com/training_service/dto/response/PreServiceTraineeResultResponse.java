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
public class PreServiceTraineeResultResponse extends BaseResponse {

    private LocalDate startDate;
    private LocalDate endDate;
    private String semester;
    private UUID traineeId;
    private UUID courseId;
    private Double result;
    private String decision;
    private String remark;
}
