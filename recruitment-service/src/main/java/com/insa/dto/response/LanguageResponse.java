package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageResponse extends BaseResponse {
    private Long languageNameId;
    private String listening;
    private String speaking;
    private String reading;
    private String writing;
    private Long applicantId;
}
