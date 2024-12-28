package com.training_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedDocumentRequest {

    @NotBlank(message = "Document name cannot be blank")
    private String documentName;

    private String description;
}
