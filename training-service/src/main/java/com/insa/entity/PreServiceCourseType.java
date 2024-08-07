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
public class PreServiceCourseType extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course type cannot be blank")
    private String courseType;

    private String description;

    @OneToMany(mappedBy = "preServiceCourseType", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PreServiceCourse> preServiceCourses;
}
