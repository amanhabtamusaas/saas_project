package com.insa.entity;

import com.insa.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "applicant_language")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Language name id cannot be null")
    private Long languageNameId;

    @NotNull(message = "Listening skill level cannot be null")
    @Enumerated(EnumType.STRING)
    private Listening listening;

    @NotNull(message = "Speaking skill level cannot be null")
    @Enumerated(EnumType.STRING)
    private Speaking speaking;

    @NotNull(message = "Reading skill level cannot be null")
    @Enumerated(EnumType.STRING)
    private Reading reading;

    @NotNull(message = "Writing skill level cannot be null")
    @Enumerated(EnumType.STRING)
    private Writing writing;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false, updatable = false)
    private Applicant applicant;
}
