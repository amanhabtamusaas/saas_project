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
public class LanguageName extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "language_name_id")
    private Long id;

    @NotBlank(message = "Language name cannot be blank")
    private String languageName;

    private String description;

    @OneToMany(mappedBy = "languageName", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Language> languages;
}
