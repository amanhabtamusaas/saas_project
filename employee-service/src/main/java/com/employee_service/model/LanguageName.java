package com.employee_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageName extends Base{

    @NotBlank(message = "Language name cannot be blank")
    private String languageName;

    private String description;

    @OneToMany(mappedBy = "languageName", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Language> languages;
}
