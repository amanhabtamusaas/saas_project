package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillResponse extends BaseResponse {
    private String skillType;
    private String skillLevel;
    private String description;
    private Long employeeId;
}
