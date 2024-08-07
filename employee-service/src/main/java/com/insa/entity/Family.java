package com.insa.entity;

import com.insa.enums.Gender;
import com.insa.enums.RelationshipType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class Family extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "family_id")
    private Long id;

    @NotNull(message = "Relationship cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "relationship")
    private RelationshipType relationshipType;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String houseNumber;
    private String homeTelephone;
    private String officeTelephone;

    @Pattern(regexp = "\\d{10}", message = "Invalid mobile number")
    private String mobileNumber;

    @Email(message = "Invalid email address")
    private String email;

    private String poBox;

    @NotNull(message = "Emergency contact flag cannot be null")
    private boolean isEmergencyContact;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;
}
