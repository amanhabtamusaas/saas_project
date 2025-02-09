package com.employee_service.model;

import com.employee_service.enums.EmploymentType;
import com.employee_service.enums.Gender;
import com.employee_service.enums.MaritalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends Base {

    @NotBlank(message = "Employee ID cannot be blank")
    private String employeeId;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Gender cannot be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Marital status cannot be null")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @NotNull(message = "Employment type cannot be null")
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @NotNull(message = "Hired date cannot be null")
    @PastOrPresent(message = "hired date must be in the past or present")
    private LocalDate hiredDate;

    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDate endDate;

    @NotNull(message = "Department ID cannot be null")
    private UUID departmentId;

    @NotNull(message = "Job Id cannot be null")
    private UUID jobId;

    @NotNull(message = "Pay Grade ID cannot be null")
    private UUID payGradeId;

    private String nationality;
    private String dutyStation;
    private String fydaNumber;
    private String passportNumber;
    private String tinNumber;
    private String pensionNumber;

    private String profileImageName;
    private String profileImageType;
    @Lob
    @Column(length = 50000000)
    private byte[] profileImageBytes;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Address> addresses;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Education> educations;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Family> families;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Language> languages;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Reference> references;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Skill> skills;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Training> trainings;

    @ManyToOne
    @JoinColumn(name = "title_name_id")
    private TitleName titleName;

    @AssertTrue(message = "End date must be after or the same day as hired date")
    public boolean isEndDateValid() {
        if (hiredDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return !endDate.isBefore(hiredDate);
    }
}
