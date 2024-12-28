package com.training_service.model;

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
public class TrainingParticipant extends Base {

    @NotNull(message = "Participant employee id cannot be null")
    private UUID participantEmployeeId;

    private String reason;

    @ManyToOne()
    @JoinColumn(name = "training_id")
    private Training training;
}
