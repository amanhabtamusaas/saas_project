package com.insa.entity;

import com.insa.enums.Decision;
import com.insa.enums.Semester;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceTraineeResult extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "result_id")
    private Long id;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be in past or present")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @PastOrPresent(message = "End date must be in past or present")
    private LocalDate endDate;

    @NotNull(message = "Semester cannot be null")
    @Enumerated(EnumType.STRING)
    private Semester semester;

    @NotNull(message = "Result cannot be null")
    private Double result;

    @NotNull(message = "Decision cannot be null")
    @Enumerated(EnumType.STRING)
    private Decision decision;

    private String remark;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private PreServiceTrainee preServiceTrainee;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private PreServiceCourse preServiceCourse;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
