package com.insa.leave_service.entity;

import com.insa.leave_service.enums.Day;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private Long employeeId;
    @NotNull
    @Column(nullable = false)
    private int numberOfDays;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate leaveStart;
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Day day;
    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;
    @Size(max = 1000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "leaveType_id")
    private LeaveType leaveType;
    @ManyToOne
    @JoinColumn(name = "budgetYear_id")
    private BudgetYear budgetYear;
    @OneToMany(mappedBy = "leaveRequest", cascade = CascadeType.ALL)
    private List<LeaveApproveDepartment> leaveApproveDepartments;
    private Long tenantId;


}