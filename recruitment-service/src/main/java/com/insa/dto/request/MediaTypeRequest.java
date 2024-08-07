package com.insa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaTypeRequest {

    @NotBlank(message = "Media type name cannot be blank")
    private String mediaTypeName;

    private String description;
}
