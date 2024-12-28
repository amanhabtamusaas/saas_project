package com.training_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true, exclude = "preServiceTrainees")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedDocument extends Base{

    @NotBlank(message = "Document name cannot be blank")
    private String documentName;

    private String description;

    @ToString.Exclude
    @ManyToMany(mappedBy = "checkedDocuments", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<PreServiceTrainee> preServiceTrainees;
}
