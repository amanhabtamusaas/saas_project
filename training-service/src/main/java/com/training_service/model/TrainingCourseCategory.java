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
public class TrainingCourseCategory extends Base {

    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;

    private String description;

    @OneToMany(mappedBy = "trainingCourseCategory", fetch = FetchType.LAZY)
    private List<Training> trainings;
}
