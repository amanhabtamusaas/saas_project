package com.insa.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequest {

    @FutureOrPresent(message = "Start date must be in the future or present")
    private LocalDateTime startDate;

    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDateTime endDate;

    @NotNull(message = "Announcement type cannot be null")
    private String announcementType;

    @NotNull(message = "Media type cannot be null")
    private Set<Long> mediaTypeIds;

    @NotNull(message = "Occurrence cannot be null")
    private Integer occurrence;

    @NotNull(message = "Recruitment cannot be null")
    private Long recruitmentId;
}
