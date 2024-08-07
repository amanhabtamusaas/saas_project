package com.insa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.insa.enums.InternshipStatus;
import com.insa.enums.Semester;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true, exclude = "internshipPayment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipStudent extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "intern_id")
    private Long id;

    @NotNull(message = "Budget year id cannot be null")
    private Long budgetYearId;

    @NotNull(message = "Semester cannot be null")
    @Enumerated(EnumType.STRING)
    private Semester semester;

    private Long placedDepartmentId;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in future or present")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in future or present")
    private LocalDate endDate;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    private String idNumber;

    @NotNull(message = "phoneNumber cannot be null")
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Invalid phoneNumber number")
    private String phoneNumber;

    @NotBlank(message = "Stream cannot be blank")
    private String stream;

    @NotNull(message = "Location id cannot be null")
    private Long locationId;

    @Enumerated(EnumType.STRING)
    private InternshipStatus internshipStatus;

    private String remark;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @OneToOne(mappedBy = "internshipStudent", cascade = CascadeType.ALL)
    private InternshipPayment internshipPayment;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
