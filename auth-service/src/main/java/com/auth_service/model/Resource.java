package com.auth_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource extends Base{

    @NotBlank(message = "Resource name cannot be blank")
    private String resourceName;

    @NotBlank(message = "Group name cannot be blank")
    private String groupName;

    private Set<String> requiredRoles;

    private String description;
}
