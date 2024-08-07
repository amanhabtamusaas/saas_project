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
public class PreServiceTraineeResultResponse extends BaseResponse {

    private LocalDate startDate;
    private LocalDate endDate;
    private String semester;
    private Long traineeId;
    private Long courseId;
    private Double result;
    private String decision;
    private String remark;
}
