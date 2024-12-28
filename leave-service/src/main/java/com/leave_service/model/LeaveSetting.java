package com.leave_service.model;
import com.leave_service.enums.EmploymentType;
import com.leave_service.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class LeaveSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "employmentType")
    private EmploymentType employmentType;

    @NotNull
    @Column(nullable = false)
    private int minimumDays;

    @NotNull
    @Column(nullable = false)
    private int maximumDays;

    @Size(max = 255)
    @Column(nullable = true)
    private String remark;

    @ManyToOne
    @JoinColumn(name = "leaveType_id")
    private LeaveType leaveType;

    private Boolean toBalance;
    private Boolean escapeSunday;
    private Boolean escapeSaturday;
    private Boolean escapeHoliday;
    private UUID tenantId;


}