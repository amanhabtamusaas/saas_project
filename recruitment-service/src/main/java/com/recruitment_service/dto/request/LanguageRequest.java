package com.recruitment_service.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageRequest {
    @NotNull(message = "Language name id cannot be null")
    private UUID languageNameId;

    @NotNull(message = "Listening skill level cannot be null")
    private String listening;

    @NotNull(message = "Speaking skill level cannot be null")
    private String speaking;

    @NotNull(message = "Reading skill level cannot be null")
    private String reading;

    @NotNull(message = "Writing skill level cannot be null")
    private String writing;
}
