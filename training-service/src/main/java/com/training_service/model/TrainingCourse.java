package com.training_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingCourse extends Base {

    @NotBlank(message = "Course name cannot be blank")
    private String courseName;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "course_category_id")
    private TrainingCourseCategory trainingCourseCategory;

    @OneToMany(mappedBy = "trainingCourse", fetch = FetchType.LAZY)
    private List<Training> trainings;
}
