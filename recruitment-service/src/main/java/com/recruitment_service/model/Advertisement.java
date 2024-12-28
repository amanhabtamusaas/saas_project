package com.recruitment_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.recruitment_service.enums.AnnouncementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true,
        exclude = {"recruitment", "mediaTypes"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advertisement extends Base {

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the future or present")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDateTime endDate;

    @NotNull(message = "Announcement type cannot be null")
    @Enumerated(EnumType.STRING)
    private AnnouncementType announcementType;

    @NotNull(message = "Occurrence cannot be null")
    private Integer occurrence;

    @OneToOne
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "advertisement_media_type",
            joinColumns = @JoinColumn(name = "advertisement_id"),
            inverseJoinColumns = @JoinColumn(name = "media_type_id")
    )
    @JsonManagedReference
    private Set<MediaType> mediaTypes = new HashSet<>();

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
