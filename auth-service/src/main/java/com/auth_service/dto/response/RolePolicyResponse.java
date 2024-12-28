package com.auth_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePolicyResponse {

    private String policyId;
    private String policyName;
    private List<String> roleNames;
    private String description;
}
