package com.insa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "preServiceTrainees")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedDocument extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "document_id")
    private Long id;

    @NotBlank(message = "Document name cannot be blank")
    private String documentName;

    private String description;


    @ToString.Exclude
    @ManyToMany(mappedBy = "checkedDocuments", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<PreServiceTrainee> preServiceTrainees;
}
