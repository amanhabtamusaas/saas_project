package com.employee_service.model;

import com.employee_service.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language extends Base {

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
    @JoinColumn(name = "employee_id", nullable = false, updatable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "language_name_id")
    private LanguageName languageName;}
