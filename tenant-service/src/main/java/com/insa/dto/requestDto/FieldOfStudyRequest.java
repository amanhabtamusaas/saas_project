package com.insa.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldOfStudyRequest {
    private String fieldOfStudy;
    private String description;


    // Getters and Setters
}