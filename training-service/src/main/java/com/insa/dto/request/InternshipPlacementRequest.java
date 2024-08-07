package com.insa.dto.request;

import com.insa.entity.InternshipStudent;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipPlacementRequest {

    @NotNull(message = "Placed department Id cannot be null")
    private Long placedDepartmentId;

    private String remark;
}
