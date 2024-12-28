package com.training_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.training_service.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true,
        exclude = {"preServiceCourses", "checkedDocuments"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceTrainee extends Base{

    @NotBlank(message = "Trainee id cannot be blank")
    private String traineeId;

    @NotNull(message = "Budget year id cannot be null")
    private UUID budgetYearId;

    @NotBlank(message = "Batch code cannot be blank")
    private String batchCode;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Amharic first name cannot be blank")
    private String amharicFirstName;

    @NotBlank(message = "Amharic middle name cannot be blank")
    private String amharicMiddleName;

    @NotBlank(message = "Amharic last name cannot be blank")
    private String amharicLastName;

    @NotNull(message = "Gender cannot be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Location id cannot be null")
    private UUID locationId;

    private String telephoneNumber;

    @NotBlank(message = "Mobile number cannot be blank")
    private String mobileNumber;

    @NotNull(message = "Education level id cannot be null")
    private UUID educationLevelId;

    @NotNull(message = "Field of study id cannot be null")
    private UUID fieldOfStudyId;

    private String remark;

    private String imageName;
    private String imageType;
    @Lob
    @Column(length = 50000000)
    private byte[] image;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "trainee_checked_document",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    @JsonManagedReference
    private Set<CheckedDocument> checkedDocuments;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "pre_service_trainee_course",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonManagedReference
    private Set<PreServiceCourse> preServiceCourses;

    @OneToMany(mappedBy = "preServiceTrainee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PreServiceTraineeResult> preServiceTraineeResults;
}
