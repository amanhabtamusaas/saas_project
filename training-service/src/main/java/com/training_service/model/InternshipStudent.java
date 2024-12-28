package com.training_service.model;

import com.training_service.enums.InternshipStatus;
import com.training_service.enums.Semester;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true, exclude = "internshipPayment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipStudent extends Base{

    @NotNull(message = "Budget year id cannot be null")
    private UUID budgetYearId;

    @NotNull(message = "Semester cannot be null")
    @Enumerated(EnumType.STRING)
    private Semester semester;

    private UUID placedDepartmentId;

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
    private UUID locationId;

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
