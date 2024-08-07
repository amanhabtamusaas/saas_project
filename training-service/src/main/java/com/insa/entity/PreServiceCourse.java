package com.insa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "preServiceTrainees")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreServiceCourse extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "course_id")
    private Long id;

    @NotBlank(message = "Course name cannot be blank")
    private String courseName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "course_type_id")
    private PreServiceCourseType preServiceCourseType;

    @ManyToMany(mappedBy = "preServiceCourses", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<PreServiceTrainee> preServiceTrainees;

    @OneToMany(mappedBy = "preServiceCourse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PreServiceTraineeResult> preServiceTraineeResults;
}
