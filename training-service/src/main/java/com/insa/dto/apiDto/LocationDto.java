package com.insa.dto.apiDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
        private Long id;
        private String locationName;
        private Long parentLocationId;
        private List<Long> subLocationIds;
        private Long locationTypeId;
        private Long tenantId;
}
