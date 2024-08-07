package com.insa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingParticipant extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "participant_id")
    private Long id;

    @NotNull(message = "Participant employee id cannot be null")
    private Long participantEmployeeId;

    private String reason;

    @ManyToOne()
    @JoinColumn(name = "training_id")
    private Training training;
}
