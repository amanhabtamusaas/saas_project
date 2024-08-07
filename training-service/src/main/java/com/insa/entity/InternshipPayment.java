package com.insa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true, exclude = "internshipStudent")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternshipPayment extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "payment_id")
    private Long id;

    @NotBlank(message = "Reference letter cannot be blank")
    private String referenceLetter;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;

    @NotNull(message = "Payment amount cannot be null")
    @Min(value = 0, message = "Payment amount must be non-negative")
    private Double paymentAmount;

    private String remark;

    @OneToOne
    @JoinColumn(name = "intern_id")
    private InternshipStudent internshipStudent;

    @AssertTrue(message = "End date must be after or the same day as start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(startDate);
    }
}
