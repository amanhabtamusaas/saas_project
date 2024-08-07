package com.insa.dto.request;

import com.insa.enums.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageRequest {

    @NotNull(message = "Language name id cannot be null")
    private Long languageNameId;

    @NotNull(message = "Listening skill level cannot be null")
    private String listening;

    @NotNull(message = "Speaking skill level cannot be null")
    private String speaking;

    @NotNull(message = "Reading skill level cannot be null")
    private String reading;

    @NotNull(message = "Writing skill level cannot be null")
    private String writing;
}
