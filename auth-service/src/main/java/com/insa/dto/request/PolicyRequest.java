package com.insa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyRequest {

    private String policyName;
    private Set<String> roleNames;
    private String description;
}
