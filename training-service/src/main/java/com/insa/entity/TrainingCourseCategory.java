package com.insa.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "category_id")
    private Long id;

    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;

    private String description;

    @OneToMany(mappedBy = "trainingCourseCategory", fetch = FetchType.LAZY)
    private List<Training> trainings;
}
