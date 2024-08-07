package com.insa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceResponse {

    private String id;
    private String resourceName;
    private Set<String> uris;
}
