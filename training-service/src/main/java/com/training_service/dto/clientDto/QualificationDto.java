package com.training_service.dto.clientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualificationDto {
    private UUID id;
    private String qualification;
    private String description;
    private UUID tenantId;
}
