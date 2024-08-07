package com.insa.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
        private Long id;
        private String locationName;
        private Long parentLocationId;
        private List<Long> subLocationIds;
        private Long locationTypeId;
        private Long tenantId;
}
