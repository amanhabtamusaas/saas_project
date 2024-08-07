package com.insa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualTrainingPlan extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "training_plan_id")
    private Long id;

    @NotNull(message = "Department id cannot be null")
    private Long departmentId;

    @NotNull(message = "Budget year id cannot be null")
    private Long budgetYearId;

    @NotNull(message = "Number of participants cannot be null")
    private Integer numberOfParticipants;

    @NotNull(message = "Number of trainees cannot be null")
    private Integer numberOfDays;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in future or present")
    private LocalDate startDate;

    @FutureOrPresent(message = "Start date must be in future or present")
    private LocalDate endDate;

    @NotNull(message = "Cost per person cannot be null")
    @DecimalMin(value = "0.0", message = "Cost per person must be non-negative")
    private Double costPerPerson;

    @NotNull(message = "Round cannot be null")
    private Integer round;

    @NotBlank(message = "Venue cannot be blank")
    private String venue;

    private String remark;

    @ManyToOne()
    @JoinColumn(name = "course_category_id")
    private TrainingCourseCategory trainingCourseCategory;

    @ManyToOne()
    @JoinColumn(name = "training_course_id")
    private TrainingCourse trainingCourse;

    @ManyToOne()
    @JoinColumn(name = "training_institution_id")
    private TrainingInstitution trainingInstitution;

    @OneToMany(mappedBy = "annualTrainingPlan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Training> trainings;

    @AssertTrue(message = "End date must be equal to or greater than the start date plus the number of days minus one")
    private boolean isEndDateValid() {
        if (endDate == null) {
            return false;
        }
        return endDate.isEqual(startDate.plusDays(numberOfDays - 1)) ||
                endDate.isAfter(startDate.plusDays(numberOfDays - 1));
    }
}
