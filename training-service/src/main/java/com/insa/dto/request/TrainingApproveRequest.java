package com.insa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingApproveRequest {

    @NotBlank(message = "Decision cannot be blank")
    private String decision;

    private String remark;
}
