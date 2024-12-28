package com.recruitment_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "advertisements")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaType extends Base{

    @NotBlank(message = "Media type name cannot be blank")
    private String mediaTypeName;

    private String description;

    @ManyToMany(mappedBy = "mediaTypes", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST
    )
    private Set<Advertisement> advertisements = new HashSet<>();
}
