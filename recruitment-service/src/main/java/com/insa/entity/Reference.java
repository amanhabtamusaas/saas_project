package com.insa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applicant_reference")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reference extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reference_id", unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @Pattern(regexp = "\\d{10}", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @NotBlank(message = "Work address cannot be blank")
    private String workAddress;

    @Email(message = "Invalid email address")
    private String email;

    private String description;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false, updatable = false)
    private Applicant applicant;
}
